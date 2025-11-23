package br.com.lorijr.cfm_api.service;

import br.com.lorijr.cfm_api.domain.CartaoDeCredito;
import br.com.lorijr.cfm_api.domain.CompraCartao;
import br.com.lorijr.cfm_api.domain.Pessoa;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CartaoRequestDTO;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CartaoResponseDTO;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CompraCartaoRequestDTO;
import br.com.lorijr.cfm_api.exceptions.CartaoNaoEncontradoException;
import br.com.lorijr.cfm_api.exceptions.PessoaNaoEcontradaException;
import br.com.lorijr.cfm_api.mapper.CartaoMapper;
import br.com.lorijr.cfm_api.repository.CartaoRepository;
import br.com.lorijr.cfm_api.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartaoDeCreditoService {

    private final CartaoRepository cartaoRepository;
    private final PessoaRepository pessoaRepository;
    private final CartaoMapper mapper;

    @Transactional
    public CartaoResponseDTO criarCartao(CartaoRequestDTO requestDTO){
        Pessoa titular = pessoaRepository.findById(requestDTO.getTitularId())
                .orElseThrow(() -> new PessoaNaoEcontradaException("Titular não encontrado"));

        CartaoDeCredito cartao = cartaoRepository.save(mapper.dtoToEntity(requestDTO));
        cartao.setTitular(titular);

        return mapper.cartaoToDTO(cartaoRepository.save(cartao));
    }

    @Transactional
    public void adicionarCompra(Long cartaoId, CompraCartaoRequestDTO compraDTO){
        CartaoDeCredito cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado"));
        CompraCartao novaCompra = mapper.toCompraEntity(compraDTO);
        novaCompra.setCartaoDeCredito(cartao);
        cartao.getCompras().add(novaCompra);
        cartaoRepository.save(cartao);
    }

    @Transactional(readOnly = true)
    public List<CartaoResponseDTO> listarPorPessoa(Long pessoaId) {
        if (!pessoaRepository.existsById(pessoaId)) {
            throw new PessoaNaoEcontradaException("Pessoa não encontrada");
        }
        return cartaoRepository.findByTitularId(pessoaId).stream()
                .map(mapper::cartaoToDTO)
                .toList();
    }
}
