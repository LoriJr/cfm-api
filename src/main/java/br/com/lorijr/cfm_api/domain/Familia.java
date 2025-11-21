package br.com.lorijr.cfm_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_familia")
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "nome_familia", nullable = false)
    private String nomeDaFamilia;

    @OneToMany(mappedBy = "familia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pessoa> membrosDaFamilia = new ArrayList<>();

    public BigDecimal getTotalRenda(){
        if(membrosDaFamilia == null || membrosDaFamilia.isEmpty()){
            return BigDecimal.ZERO;
        }
        return membrosDaFamilia.stream()
                .filter(Objects::nonNull)
                .map(Pessoa::getTotalSalario)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
