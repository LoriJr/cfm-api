package br.com.lorijr.cfm_api.service;

import br.com.lorijr.cfm_api.domain.Pessoa;
import br.com.lorijr.cfm_api.domain.Salario;
import br.com.lorijr.cfm_api.dto.pessoa.PessoaRequestDTO;
import br.com.lorijr.cfm_api.dto.pessoa.PessoaResponseDTO;
import br.com.lorijr.cfm_api.exceptions.PessoaNaoEcontradaException;
import br.com.lorijr.cfm_api.exceptions.SalarioNaoEncontradoException;
import br.com.lorijr.cfm_api.mapper.PessoaMapper;
import br.com.lorijr.cfm_api.repository.PessoaRepository;
import br.com.lorijr.cfm_api.repository.SalarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PessoaService {

    private final PessoaRepository repository;
    private final SalarioRepository salarioRepository;
    private final PessoaMapper mapper;

    @Transactional
    public PessoaResponseDTO criarPessoa(PessoaRequestDTO requestDTO) {
        Pessoa pessoa = repository.save(mapper.toEntity(requestDTO));

        if (requestDTO.getSalarioIds() != null && !requestDTO.getSalarioIds().isEmpty()) {
            List<Salario> salariosVinculados = vincularSalarios(pessoa, requestDTO.getSalarioIds());

            // CRUCIAL: Atualiza a lista do objeto em memória para o retorno do Mapper ser preenchido
            pessoa.getSalarios().addAll(salariosVinculados);
        }

        return mapper.toPessoaDTO(pessoa);
    }

    @Transactional(readOnly = true)
    public List<PessoaResponseDTO> buscarPessoas() {
        return repository.findAll().stream()
                .map(mapper::toPessoaDTO).toList();
    }

    @Transactional(readOnly = true)
    public PessoaResponseDTO buscarPessoaPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toPessoaDTO)
                .orElseThrow(() -> new PessoaNaoEcontradaException("Pessoa não encontrada"));
    }

    public void deletarPessoa(Long id){
        repository.findById(id)
                .ifPresentOrElse(
                        repository::delete,
                        () -> { throw new PessoaNaoEcontradaException("Pessoa não encontrada"); });
    }

    @Transactional
    public PessoaResponseDTO atualizarPessoa(Long id, PessoaRequestDTO requestDTO) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new PessoaNaoEcontradaException("Pessoa não encontrada"));
        mapper.updatePessoa(requestDTO, pessoa);

        if(requestDTO.getSalarioIds() !=null){
            atualizarVinculoSalarios(pessoa, requestDTO.getSalarioIds());
        }

        Pessoa pessoaAtualizado = repository.save(pessoa);
        return mapper.toPessoaDTO(pessoaAtualizado);
    }

    private void atualizarVinculoSalarios(Pessoa pessoa, List<Long> salarioIds){
        List<Salario> novosSalarios = salarioRepository.findAllById(salarioIds);
        //vincula e valida
        novosSalarios.forEach(s -> s.setPessoa(pessoa));
        pessoa.setSalarios(novosSalarios);
    }

    private List<Salario> vincularSalarios(Pessoa pessoa, List<Long> ids) {
        List<Salario> salarios = salarioRepository.findAllById(ids);

        if (salarios.size() != ids.size()) {
            throw new SalarioNaoEncontradoException("Um ou mais salários não foram encontrados.");
        }

        salarios.forEach(salario -> {
            if (salario.getPessoa() != null) {
                // Opcional: Bloquear roubo de salário de outra pessoa
                throw new IllegalArgumentException("O salário id " + salario.getId() + " já pertence a outra pessoa.");
            }
            salario.setPessoa(pessoa); // Atualiza a FK no objeto Salario
        });

        // Salva as alterações na tabela de Salário (FKs atualizadas)
        return salarioRepository.saveAll(salarios);
    }



}
