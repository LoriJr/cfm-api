package br.com.lorijr.cfm_api.service;

import br.com.lorijr.cfm_api.domain.Familia;
import br.com.lorijr.cfm_api.domain.Pessoa;
import br.com.lorijr.cfm_api.dto.familia.FamiliaRequestDTO;
import br.com.lorijr.cfm_api.dto.familia.FamiliaResponseDTO;
import br.com.lorijr.cfm_api.mapper.FamiliaMapper;
import br.com.lorijr.cfm_api.repository.FamiliaRepository;
import br.com.lorijr.cfm_api.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FamiliaService {

    private final FamiliaRepository repository;
    private final PessoaRepository pessoaRepository;
    private final FamiliaMapper mapper;

    @Transactional
    public FamiliaResponseDTO salvarFamilia(FamiliaRequestDTO requestDTO){
        Familia familia = repository.save(mapper.familiaToEntity(requestDTO));

        if(requestDTO.getMembrosIds() !=null && !requestDTO.getMembrosIds().isEmpty()){
            List<Pessoa> membrosVinculados = vincularMembroDaFamilia(familia, requestDTO.getMembrosIds());

            familia.getMembrosDaFamilia().addAll(membrosVinculados);
        }
        return mapper.familiaToDTO(familia);
    }

    @Transactional(readOnly = true)
    public List<FamiliaResponseDTO> buscarFamilia(){
        return repository.findAll()
                .stream()
                .map(mapper::familiaToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public FamiliaResponseDTO buscarPorId(Long id){
        return repository.findById(id)
                .map(mapper::familiaToDTO)
                .orElseThrow(()-> new RuntimeException("Id de Família não encontrado"));
    }

    public void deletarFamilia(Long id) {
        repository.findById(id)
                .ifPresentOrElse(
                        repository::delete,
                        () -> { throw new RuntimeException("Id de Família não encontrado"); }
                );
    }

    public FamiliaResponseDTO atualizarFamilia(Long id, FamiliaRequestDTO requestDTO){
        Familia familia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id de Família não encontrado"));
        mapper.atualizarFamilia(requestDTO, familia);

        Familia familiaAtualizado = repository.save(familia);
        return mapper.familiaToDTO(familiaAtualizado);
    }

    private List<Pessoa> vincularMembroDaFamilia(Familia familia, List<Long> ids){
        List<Pessoa> membros = pessoaRepository.findAllById(ids);

        if(membros.size() != ids.size()){
            throw new IllegalArgumentException("Alguns membros da família não forem encontrados.");
        }
        membros.forEach(pessoa -> pessoa.setFamilia(familia));
        return pessoaRepository.saveAll(membros);
    }
}
