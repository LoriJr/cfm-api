package br.com.lorijr.cfm_api.dto.despesa;

import br.com.lorijr.cfm_api.enums.Periodo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaRequestDTO {

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;

    @Positive(message = "O valor deve ser positivo")
    @NotNull(message = "Valor não pode ser nulo")
    private BigDecimal valor;

    @NotNull(message = "Período não pode ser nulo")
    private Periodo periodo;
}
