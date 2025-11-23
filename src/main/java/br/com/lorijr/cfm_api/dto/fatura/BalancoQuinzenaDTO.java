package br.com.lorijr.cfm_api.dto.fatura;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class BalancoQuinzenaDTO {
    private BigDecimal totalEntradas; // Salários
    private BigDecimal totalSaidas;   // Contas + Cartões
    private BigDecimal saldo;         // Entradas - Saídas
    private String status;
}
