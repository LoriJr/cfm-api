package br.com.lorijr.cfm_api.mapper;

import br.com.lorijr.cfm_api.domain.Pessoa;
import br.com.lorijr.cfm_api.dto.pessoa.PessoaRequestDTO;
import br.com.lorijr.cfm_api.dto.pessoa.PessoaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses ={SalarioMapper.class})
public interface PessoaMapper {

    @Mapping(target="totalSalario", expression = "java(pessoa.getTotalSalario())")
    PessoaResponseDTO toPessoaDTO(Pessoa pessoa);

    @Mapping(target="id", ignore = true)
//    @Mapping(target = "salarios")
    Pessoa toEntity(PessoaRequestDTO requestDTO);
}
