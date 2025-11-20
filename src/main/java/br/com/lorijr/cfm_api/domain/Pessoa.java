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
@Table(name= "tb_pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salario> salarios = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "familia_id")
    private Familia familia;

    public BigDecimal getTotalSalario(){
        if (salarios == null || salarios.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return salarios.stream()
                .filter(Objects::nonNull)
                .map(Salario::getValorSalario)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
