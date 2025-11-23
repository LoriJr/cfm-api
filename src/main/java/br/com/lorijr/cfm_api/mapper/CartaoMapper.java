package br.com.lorijr.cfm_api.mapper;

import br.com.lorijr.cfm_api.domain.CartaoDeCredito;
import br.com.lorijr.cfm_api.domain.CompraCartao;
import br.com.lorijr.cfm_api.domain.Despesa;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CartaoRequestDTO;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CartaoResponseDTO;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CompraCartaoRequestDTO;
import br.com.lorijr.cfm_api.dto.despesa.DespesaRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartaoMapper {

    @Mapping(target="nomeTitular", source="titular.nome")
    @Mapping(target="totalFatura", expression="java(cartao.getTotalCompras")
    CartaoResponseDTO cartaoToDTO(CartaoDeCredito cartao);

    @Mapping(target="id", ignore = true)
    @Mapping(target = "titular", ignore = true)
    @Mapping(target = "compras", ignore = true)
    CartaoDeCredito dtoToEntity(CartaoRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cartao", ignore = true)
    CompraCartao toCompraEntity(CompraCartaoRequestDTO dto);

    Despesa toDespesa(DespesaRequestDTO dto);

}
