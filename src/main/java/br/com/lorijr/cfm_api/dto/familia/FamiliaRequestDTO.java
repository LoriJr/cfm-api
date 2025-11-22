package br.com.lorijr.cfm_api.dto.familia;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaRequestDTO {

    @NotBlank(message = "O nome da família é obrigatório")
    private String nomeDaFamilia;
    private List<Long> membrosIds;

}
