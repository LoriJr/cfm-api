package br.com.lorijr.cfm_api.dto.cartaodecredito;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartaoRequestDTO {

    @NotBlank(message = "Nome do cartão é obrigatório")
    private String nome;

    @NotNull(message = "Dia de vencimento é obrigatório")
    @Min(1) @Max(31)
    private Integer diaVencimento;

    @NotNull(message = "ID do titular é obrigatório")
    private Long titularId;
}