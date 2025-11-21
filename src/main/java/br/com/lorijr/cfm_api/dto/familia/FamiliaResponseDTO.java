package br.com.lorijr.cfm_api.dto.familia;

import br.com.lorijr.cfm_api.dto.pessoa.PessoaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamiliaResponseDTO {

    private Long id;
    private String nomeDaFamilia;
    private List<PessoaResponseDTO> membrosDaFamilia;
    private BigDecimal totalRenda;
}
