package br.com.lorijr.cfm_api.dto.familia;

import br.com.lorijr.cfm_api.dto.pessoa.PessoaResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public class FamiliaResponseDTO {

    private Long id;
    private String nomeDaFamilia;
    private List<PessoaResponseDTO> membrosDaFamilia;
    private BigDecimal totalRenda;
}
