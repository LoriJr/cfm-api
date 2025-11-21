package br.com.lorijr.cfm_api.dto.salario;

import br.com.lorijr.cfm_api.enums.Periodo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalarioRequestDTO {

    @NotNull(message = "O salário é obrigatório")
    @Positive(message = "O salário deve ser positivo")
    private BigDecimal valorSalario;

    @NotNull(message = "O período é obrigatório")
    private Periodo periodo;
}
