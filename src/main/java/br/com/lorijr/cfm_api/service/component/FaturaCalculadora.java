package br.com.lorijr.cfm_api.service.component;

import br.com.lorijr.cfm_api.domain.*;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CompraCartaoResponseDTO;
import br.com.lorijr.cfm_api.dto.fatura.BalancoQuinzenaDTO;
import br.com.lorijr.cfm_api.dto.fatura.FaturaCartaoDTO;
import br.com.lorijr.cfm_api.dto.fatura.ResumoFinanceiroDTO;
import br.com.lorijr.cfm_api.mapper.CartaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FaturaCalculadora {

    private final CartaoMapper cartaoMapper;

    public List<FaturaCartaoDTO> processarFaturasCartao(List<CartaoDeCredito> cartoes,

                                                        // Precisamos passar as compras já buscadas ou uma forma de acessá-las
                                                        // Para simplificar, vamos assumir que o Service já buscou as compras e passa aqui,
                                                        // ou mantemos a lógica de transformação simples.
                                                        List<CompraCartao> todasComprasDoMes) {

        List<FaturaCartaoDTO> resultado = new ArrayList<>();

        for (CartaoDeCredito cartao : cartoes) {
            // Filtra as compras deste cartão específico na lista geral trazida do banco
            List<CompraCartao> comprasDesteCartao = todasComprasDoMes.stream()
                    .filter(c -> c.getCartao().getId().equals(cartao.getId()))
                    .toList();

            if (comprasDesteCartao.isEmpty()) continue;

            BigDecimal total = comprasDesteCartao.stream()
                    .map(c -> c.getDespesa().getValor())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            List<CompraCartaoResponseDTO> comprasDTO = comprasDesteCartao.stream()
                    .map(cartaoMapper::toCompraDTO)
                    .toList();

            resultado.add(FaturaCartaoDTO.builder()
                    .cartaoId(cartao.getId())
                    .nomeCartao(cartao.getNomeDoCartao())
                    .diaVencimento(cartao.getDiaVencimento())
                    .titular(cartao.getTitular().getNome())
                    .compras(comprasDTO)
                    .totalFatura(total)
                    .build());
        }
        return resultado;
    }

    /**
     * O Cérebro Matemático: Calcula os totais e divide por quinzena
     */
    public ResumoFinanceiroDTO calcularResumo(Familia familia,
                                              List<ContaAvulsa> contas,
                                              List<FaturaCartaoDTO> cartoes) {

        // --- 1ª QUINZENA ---
        BigDecimal entQ1 = somarSalarios(familia, "PRIMEIRA_QUINZENA");
        BigDecimal saiContasQ1 = somarContas(contas, "PRIMEIRA_QUINZENA");
        BigDecimal saiCartaoQ1 = cartoes.stream()
                .filter(c -> c.getDiaVencimento() <= 15)
                .map(FaturaCartaoDTO::getTotalFatura)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSaiQ1 = saiContasQ1.add(saiCartaoQ1);
        BigDecimal saldoQ1 = entQ1.subtract(totalSaiQ1);

        // --- 2ª QUINZENA ---
        BigDecimal entQ2 = somarSalarios(familia, "SEGUNDA_QUINZENA");
        BigDecimal saiContasQ2 = somarContas(contas, "SEGUNDA_QUINZENA");
        BigDecimal saiCartaoQ2 = cartoes.stream()
                .filter(c -> c.getDiaVencimento() > 15)
                .map(FaturaCartaoDTO::getTotalFatura)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSaiQ2 = saiContasQ2.add(saiCartaoQ2);
        BigDecimal saldoQ2 = entQ2.subtract(totalSaiQ2);

        // --- TOTAIS GERAIS ---
        BigDecimal totalReceitas = entQ1.add(entQ2);
        BigDecimal totalDespesas = totalSaiQ1.add(totalSaiQ2);
        BigDecimal saldoFinal = totalReceitas.subtract(totalDespesas);

        return ResumoFinanceiroDTO.builder()
                .totalReceitasMes(totalReceitas)
                .totalDespesasMes(totalDespesas)
                .saldoFinalMes(saldoFinal)
                .statusCaixaMes(saldoFinal.compareTo(BigDecimal.ZERO) >= 0 ? "SUPERAVIT" : "DEFICIT")
                .primeiraQuinzena(montarBalanco(entQ1, totalSaiQ1, saldoQ1))
                .segundaQuinzena(montarBalanco(entQ2, totalSaiQ2, saldoQ2))
                .build();
    }


    // Métodos privados auxiliares continuam aqui, escondidos do Service
    private BigDecimal somarSalarios(Familia f, String periodo) {
        return f.getMembrosDaFamilia().stream()
                .flatMap(p -> p.getSalarios().stream())
                .filter(s -> periodo.equals(s.getPeriodo().name()))
                .map(Salario::getValorSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal somarContas(List<ContaAvulsa> c, String periodo) {
        return c.stream()
                .filter(conta -> periodo.equals(conta.getDespesa().getPeriodo().name()))
                .map(conta -> conta.getDespesa().getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BalancoQuinzenaDTO montarBalanco(BigDecimal ent, BigDecimal sai, BigDecimal saldo) {
        return BalancoQuinzenaDTO.builder()
                .totalEntradas(ent).totalSaidas(sai).saldo(saldo)
                .status(saldo.compareTo(BigDecimal.ZERO) >= 0 ? "POSITIVO" : "NEGATIVO")
                .build();
    }
}
