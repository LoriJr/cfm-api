package br.com.lorijr.cfm_api.dto.fatura;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ResumoFinanceiroDTO {
    private BigDecimal totalReceitasMes;
    private BigDecimal totalDespesasMes;
    private BigDecimal saldoFinalMes;
    private String statusCaixaMes;

    private BalancoQuinzenaDTO primeiraQuinzena;
    private BalancoQuinzenaDTO segundaQuinzena;
}
