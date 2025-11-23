package br.com.lorijr.cfm_api.controller;

import br.com.lorijr.cfm_api.dto.cartaodecredito.CartaoRequestDTO;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CartaoResponseDTO;
import br.com.lorijr.cfm_api.dto.cartaodecredito.CompraCartaoRequestDTO;
import br.com.lorijr.cfm_api.service.CartaoDeCreditoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cartoes")
public class CartaoDeCreditoController {

        private final CartaoDeCreditoService service;

    @PostMapping
    public ResponseEntity<CartaoResponseDTO> criar(@RequestBody @Valid CartaoRequestDTO dto) {
        CartaoResponseDTO response = service.criarCartao(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    // 2. Lançar uma Compra no Cartão
    // POST /cartoes/1/compras
    @PostMapping("/{id}/compras")
    public ResponseEntity<Void> adicionarCompra(@PathVariable Long id,@RequestBody @Valid CompraCartaoRequestDTO compraDTO) {
        service.adicionarCompra(id, compraDTO);
        return ResponseEntity.ok().build();
    }

    // 3. Listar cartões de uma pessoa
    // GET /cartoes?pessoaId=1
    @GetMapping
    public ResponseEntity<List<CartaoResponseDTO>> listar(
            @RequestParam(name = "pessoaId") Long pessoaId
    ) {
        return ResponseEntity.ok(service.listarPorPessoa(pessoaId));
    }
}
