package br.com.lorijr.cfm_api.dto.pessoa;

import br.com.lorijr.cfm_api.dto.salario.SalarioResponseDTO;
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
    private List<SalarioResponseDTO> salarios;
    private BigDecimal totalSalario;
}
