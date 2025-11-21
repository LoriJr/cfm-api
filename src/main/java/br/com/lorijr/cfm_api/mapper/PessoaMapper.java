package br.com.lorijr.cfm_api.mapper;

import br.com.lorijr.cfm_api.domain.Pessoa;
import br.com.lorijr.cfm_api.dto.pessoa.PessoaRequestDTO;
import br.com.lorijr.cfm_api.dto.pessoa.PessoaResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses ={SalarioMapper.class})
public interface PessoaMapper {

    @Mapping(target="totalSalario", expression = "java(pessoa.getTotalSalario())")
    PessoaResponseDTO toPessoaDTO(Pessoa pessoa);

    @Mapping(target="id", ignore = true)
//    @Mapping(target = "salarios")
    Pessoa toEntity(PessoaRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "salarios", ignore = true)
    @Mapping(target = "familia", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePessoa(PessoaRequestDTO dto, @MappingTarget Pessoa pessoa);
}
