package br.com.lorijr.cfm_api.controller;

import br.com.lorijr.cfm_api.dto.pessoa.PessoaRequestDTO;
import br.com.lorijr.cfm_api.dto.pessoa.PessoaResponseDTO;
import br.com.lorijr.cfm_api.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> criarPessoas(@RequestBody PessoaRequestDTO requestDTO) {

        PessoaResponseDTO response = service.criarPessoa(requestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response)
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> getPessoas(){
        List<PessoaResponseDTO> pessoas = service.buscarPessoas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> getPessoaPorId(@PathVariable Long id){
        PessoaResponseDTO response = service.buscarPessoaPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        service.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaRequestDTO requestDTO) {
        PessoaResponseDTO response = service.atualizarPessoa(id,requestDTO);
        return ResponseEntity.ok(response);
    }

}
