package br.com.lorijr.cfm_api.dto.contaavulsa;

import br.com.lorijr.cfm_api.dto.despesa.DespesaRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContaAvulsaRequestDTO {

    @NotNull
    private LocalDate dataVencimento;

    @NotNull
    @Valid
    private DespesaRequestDTO despesa;

    private Long familiaId;
}