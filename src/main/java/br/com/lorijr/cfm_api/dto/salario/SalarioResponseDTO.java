package br.com.lorijr.cfm_api.dto.salario;

import br.com.lorijr.cfm_api.enums.Periodo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalarioResponseDTO {

    private Long id;
    private BigDecimal salario;
    private Periodo periodo;
}
