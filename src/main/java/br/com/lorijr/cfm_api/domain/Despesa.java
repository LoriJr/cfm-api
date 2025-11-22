package br.com.lorijr.cfm_api.domain;

import br.com.lorijr.cfm_api.enums.Periodo;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Despesa {

    private String descricao;
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private Periodo periodo;
}
