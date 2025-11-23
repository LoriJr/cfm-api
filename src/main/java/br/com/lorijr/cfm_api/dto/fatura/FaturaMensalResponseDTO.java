package br.com.lorijr.cfm_api.dto.fatura;

import br.com.lorijr.cfm_api.dto.contaavulsa.ContaAvulsaResponseDTO;
import br.com.lorijr.cfm_api.dto.familia.FamiliaResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FaturaMensalResponseDTO {
    private String mes;
    private Integer ano;

    private ResumoFinanceiroDTO resumo;

    private FamiliaResponseDTO familia;
    private List<ContaAvulsaResponseDTO> contasAvulsas;
    private List<FaturaCartaoDTO> faturasCartoes;
}
