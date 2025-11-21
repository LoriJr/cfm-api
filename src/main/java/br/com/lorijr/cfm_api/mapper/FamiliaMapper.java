package br.com.lorijr.cfm_api.mapper;

import br.com.lorijr.cfm_api.domain.Familia;
import br.com.lorijr.cfm_api.dto.familia.FamiliaRequestDTO;
import br.com.lorijr.cfm_api.dto.familia.FamiliaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface FamiliaMapper {

    @Mapping(target = "totalRenda", expression = "java(familia.getTotalRenda())")
    FamiliaResponseDTO familiaToDTO(Familia familia);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "membrosDaFamilia", ignore = true)
    Familia familiaToEntity(FamiliaRequestDTO requestDTO);

}
