package br.com.lorijr.cfm_api.service;

import br.com.lorijr.cfm_api.domain.CartaoDeCredito;
import br.com.lorijr.cfm_api.domain.CompraCartao;
import br.com.lorijr.cfm_api.domain.ContaAvulsa;
import br.com.lorijr.cfm_api.domain.Familia;
import br.com.lorijr.cfm_api.dto.fatura.FaturaCartaoDTO;
import br.com.lorijr.cfm_api.dto.fatura.FaturaMensalResponseDTO;
import br.com.lorijr.cfm_api.dto.fatura.ResumoFinanceiroDTO;
import br.com.lorijr.cfm_api.exceptions.FamiliaNaoEncontradaException;
import br.com.lorijr.cfm_api.mapper.CartaoMapper;
import br.com.lorijr.cfm_api.mapper.ContaAvulsaMapper;
import br.com.lorijr.cfm_api.mapper.FamiliaMapper;
import br.com.lorijr.cfm_api.repository.CartaoRepository;
import br.com.lorijr.cfm_api.repository.CompraCartaoRepository;
import br.com.lorijr.cfm_api.repository.ContaAvulsaRepository;
import br.com.lorijr.cfm_api.repository.FamiliaRepository;
import br.com.lorijr.cfm_api.service.component.FaturaCalculadora;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FaturaService {

    private final FamiliaRepository familiaRepository;
    private final ContaAvulsaRepository contaAvulsaRepository;
    private final CartaoRepository cartaoRepository;
    private final CompraCartaoRepository compraCartaoRepository;

    private final FamiliaMapper familiaMapper;
    private final ContaAvulsaMapper contaAvulsaMapper;
    private final CartaoMapper cartaoMapper;
    private final FaturaCalculadora calculadora;

    @Transactional(readOnly = true)
    public FaturaMensalResponseDTO gerarFatura(Long familiaId, int mes, int ano) {
        // 1. Definição de Datas
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.with(TemporalAdjusters.lastDayOfMonth());

        // 2. Busca de Dados (IO / Banco de Dados)
        Familia familia = familiaRepository.findById(familiaId)
                .orElseThrow(() -> new FamiliaNaoEncontradaException("Família não encontrada"));

        List<ContaAvulsa> contas = contaAvulsaRepository.buscarPorMes(familiaId, inicio, fim);
        List<CartaoDeCredito> cartoes = cartaoRepository.buscarPorFamilia(familiaId);

        // Query otimizada: Busca compras de todos os cartões dessa família neste período
        // (Você precisará criar esse método no Repository ou manter o loop antigo,
        // mas para passar pra calculadora, ter a lista pronta é melhor)
        List<CompraCartao> todasCompras = compraCartaoRepository.buscarComprasPorFamiliaEPeriodo(familiaId, inicio, fim);

        // 3. Processamento (Delega para a Calculadora)
        List<FaturaCartaoDTO> faturasCartoes = calculadora.processarFaturasCartao(cartoes, todasCompras);
        ResumoFinanceiroDTO resumo = calculadora.calcularResumo(familia, contas, faturasCartoes);

        // 4. Mapeamento Final e Retorno
        return FaturaMensalResponseDTO.builder()
                .mes(inicio.getMonth().name())
                .ano(ano)
                .familia(familiaMapper.familiaToDTO(familia))
                .contasAvulsas(contas.stream().map(contaAvulsaMapper::toDTO).toList())
                .faturasCartoes(faturasCartoes)
                .resumo(resumo)
                .build();
    }

}
