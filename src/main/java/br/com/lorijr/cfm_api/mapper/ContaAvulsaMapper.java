package br.com.lorijr.cfm_api.mapper;

import br.com.lorijr.cfm_api.domain.ContaAvulsa;
import br.com.lorijr.cfm_api.domain.Despesa;
import br.com.lorijr.cfm_api.dto.contaavulsa.ContaAvulsaRequestDTO;
import br.com.lorijr.cfm_api.dto.despesa.DespesaRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContaAvulsaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "familia", ignore = true) // Service busca pelo ID
    ContaAvulsa toEntity(ContaAvulsaRequestDTO dto);

    Despesa toDespesa(DespesaRequestDTO despesaDTO);
}
