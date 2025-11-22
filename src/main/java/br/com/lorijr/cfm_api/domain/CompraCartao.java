package br.com.lorijr.cfm_api.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tb_item_cartao")
public class CompraCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Embedded
    private Despesa despesa;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private CartaoDeCredito cartaoDeCredito;
}
