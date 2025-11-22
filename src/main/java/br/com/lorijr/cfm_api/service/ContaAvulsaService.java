package br.com.lorijr.cfm_api.service;

import br.com.lorijr.cfm_api.domain.ContaAvulsa;
import br.com.lorijr.cfm_api.domain.Familia;
import br.com.lorijr.cfm_api.dto.contaavulsa.ContaAvulsaRequestDTO;
import br.com.lorijr.cfm_api.dto.contaavulsa.ContaAvulsaResponseDTO;
import br.com.lorijr.cfm_api.exceptions.FamiliaNaoEncontradaException;
import br.com.lorijr.cfm_api.mapper.ContaAvulsaMapper;
import br.com.lorijr.cfm_api.repository.ContaAvulsaRepository;
import br.com.lorijr.cfm_api.repository.FamiliaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContaAvulsaService {

    private final ContaAvulsaRepository contaAvulsaRepository;
    private final FamiliaRepository familiaRepository;
    private final ContaAvulsaMapper mapper;

    @Transactional
    public ContaAvulsaResponseDTO criar(ContaAvulsaRequestDTO requestDTO){
        Familia familia = familiaRepository.findById(requestDTO.getFamiliaId())
                .orElseThrow(() -> new FamiliaNaoEncontradaException("Familia não encontrada"));
        ContaAvulsa conta = mapper.toEntity(requestDTO);
        conta.setFamilia(familia);
        ContaAvulsa contaSalva = contaAvulsaRepository.save(conta);
        return mapper.toDTO(contaSalva);
    }

    @Transactional(readOnly = true)
    public List<ContaAvulsaResponseDTO> listarContaAvulsa(){
        return contaAvulsaRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContaAvulsaResponseDTO> findByFamiliaId(Long familiaId){
        if (!familiaRepository.existsById(familiaId)) {
            throw new FamiliaNaoEncontradaException("Família não encontrada");
        }
        return contaAvulsaRepository.findByFamiliaId(familiaId)
                .stream()
                .map(mapper::toDTO)
                .toList();

    }


}
