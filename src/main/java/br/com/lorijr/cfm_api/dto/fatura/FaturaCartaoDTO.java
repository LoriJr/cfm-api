package br.com.lorijr.cfm_api.dto.fatura;

import br.com.lorijr.cfm_api.dto.cartaodecredito.CompraCartaoResponseDTO;
import java.math.BigDecimal;
import java.util.List;

public class FaturaCartaoDTO {
    private Long cartaoId;
    private String nomeCartao;
    private Integer diaVencimento;
    private String titular;
    private BigDecimal totalFatura;
    private List<CompraCartaoResponseDTO> compras; // Lista de itens
}
