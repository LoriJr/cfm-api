package br.com.lorijr.cfm_api.service;

import br.com.lorijr.cfm_api.domain.Salario;
import br.com.lorijr.cfm_api.dto.salario.SalarioRequestDTO;
import br.com.lorijr.cfm_api.dto.salario.SalarioResponseDTO;
import br.com.lorijr.cfm_api.exceptions.SalarioNaoEncontradoException;
import br.com.lorijr.cfm_api.mapper.SalarioMapper;
import br.com.lorijr.cfm_api.repository.SalarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SalarioService {

    private final SalarioRepository repository;
    private final SalarioMapper mapper;

    @Transactional
    public SalarioResponseDTO criarSalario(SalarioRequestDTO requestDTO){
        Salario salario = repository.save(mapper.salarioToEntity(requestDTO));
        return mapper.salarioToDTO(salario);
    }

    @Transactional(readOnly = true)
    public List<SalarioResponseDTO> buscarSalarios() {
        return repository.findAll().stream()
                .map(mapper::salarioToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SalarioResponseDTO buscarPorId(Long id){
        return repository.findById(id)
                .map(mapper::salarioToDTO)
                .orElseThrow(() -> new SalarioNaoEncontradoException("Salário não encontrado"));
    }

    public void deletarSalario(Long id) {
        repository.findById(id)
                .ifPresentOrElse(
                        repository::delete,
                        () -> { throw new SalarioNaoEncontradoException("Salário não encontrado"); });

    }

    @Transactional
    public SalarioResponseDTO atualizarSalario(Long id, SalarioRequestDTO dto){
        Salario salario = repository.findById(id)
                .orElseThrow(() -> new SalarioNaoEncontradoException("Salario não encontrado"));

        mapper.atualizarSalario(dto, salario);
        Salario atualizado = repository.save(salario);
        return mapper.salarioToDTO(atualizado);
    }

}
