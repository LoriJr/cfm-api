package br.com.lorijr.cfm_api.dto.pessoa;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private List<Long> salarioIds;
}
