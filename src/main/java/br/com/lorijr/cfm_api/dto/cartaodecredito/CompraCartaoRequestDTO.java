package br.com.lorijr.cfm_api.dto.cartaodecredito;

import br.com.lorijr.cfm_api.dto.despesa.DespesaRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CompraCartaoRequestDTO {

    @NotNull
    private LocalDate dataCompra;

    @NotNull
    private Long cartaoId;

    @NotNull
    @Valid
    private DespesaRequestDTO despesa;
}