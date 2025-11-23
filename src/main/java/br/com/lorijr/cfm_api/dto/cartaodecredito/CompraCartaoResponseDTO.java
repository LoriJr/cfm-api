package br.com.lorijr.cfm_api.dto.cartaodecredito;

import br.com.lorijr.cfm_api.dto.despesa.DespesaRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompraCartaoResponseDTO {
    private Long id;
    private LocalDate dataCompra;

    private DespesaRequestDTO despesa;
}
