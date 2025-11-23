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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_cartao_de_credito")
public class CartaoDeCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_do_cartao", nullable = false)
    private String nomeDoCartao;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa titular;

    @OneToMany(mappedBy = "cartaoDeCredito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompraCartao> compras = new ArrayList<>();

    public BigDecimal getTotalCompras() {
        if(compras.isEmpty() || compras == null){
            return BigDecimal.ZERO;
        }

        return compras.stream()
                .filter(Objects::nonNull)
                .map(CompraCartao::getDespesa)
                .filter(Objects::nonNull)
                .map(Despesa::getValor)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
