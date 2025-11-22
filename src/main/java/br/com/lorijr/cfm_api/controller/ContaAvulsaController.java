package br.com.lorijr.cfm_api.controller;

import br.com.lorijr.cfm_api.dto.contaavulsa.ContaAvulsaRequestDTO;
import br.com.lorijr.cfm_api.dto.contaavulsa.ContaAvulsaResponseDTO;
import br.com.lorijr.cfm_api.service.ContaAvulsaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/conta-avulsa")
public class ContaAvulsaController {

    private final ContaAvulsaService service;

    @PostMapping
    public ResponseEntity<ContaAvulsaResponseDTO> criarConta(@RequestBody ContaAvulsaRequestDTO requestDTO){

        ContaAvulsaResponseDTO response = service.criar(requestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response)
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

//    @GetMapping
//    public ResponseEntity<List<ContaAvulsaResponseDTO>> listarContasAvulsas(){
//        return ResponseEntity.ok(service.listarContaAvulsa());
//    }

    @GetMapping
    public ResponseEntity<List<ContaAvulsaResponseDTO>> listar(
            @RequestParam(name = "familiaId", required = false) Long familiaId
    ) {
        // Chama o service passando o ID recebido na URL
        List<ContaAvulsaResponseDTO> resultado = service.findByFamiliaId(familiaId);

        return ResponseEntity.ok(resultado);
    }
}
