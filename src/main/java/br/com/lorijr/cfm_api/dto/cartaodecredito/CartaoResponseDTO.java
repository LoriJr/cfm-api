package br.com.lorijr.cfm_api.dto.cartaodecredito;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartaoResponseDTO {
    private Long id;
    private String nomeDoCartao;
    private Integer diaVencimento;
    private String nomeTitular;
    private BigDecimal totalFatura;
}