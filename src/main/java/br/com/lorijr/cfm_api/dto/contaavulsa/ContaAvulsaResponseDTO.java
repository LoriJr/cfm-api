package br.com.lorijr.cfm_api.dto.contaavulsa;

import br.com.lorijr.cfm_api.dto.despesa.DespesaRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaAvulsaResponseDTO {

    private Long id;
    private LocalDate dataVencimento;
    private DespesaRequestDTO despesa;
    private String nomeDaFamilia;
}
