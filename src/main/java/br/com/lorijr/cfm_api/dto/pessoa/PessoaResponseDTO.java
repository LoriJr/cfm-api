package br.com.lorijr.cfm_api.dto.pessoa;

import br.com.lorijr.cfm_api.domain.Salario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaResponseDTO {

    private Long id;
    private String nome;
    private List<Salario> salarios;
    private BigDecimal totalSalario;
}
