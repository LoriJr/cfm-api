package br.com.lorijr.cfm_api.mapper;

import br.com.lorijr.cfm_api.domain.Salario;
import br.com.lorijr.cfm_api.dto.salario.SalarioRequestDTO;
import br.com.lorijr.cfm_api.dto.salario.SalarioResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SalarioMapper {

    SalarioResponseDTO salarioToDTO(Salario salario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pessoa", ignore = true)
    Salario salarioToEntity(SalarioRequestDTO requestDTO);
}
