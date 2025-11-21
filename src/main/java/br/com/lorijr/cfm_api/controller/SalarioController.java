package br.com.lorijr.cfm_api.controller;

import br.com.lorijr.cfm_api.dto.salario.SalarioRequestDTO;
import br.com.lorijr.cfm_api.dto.salario.SalarioResponseDTO;
import br.com.lorijr.cfm_api.service.SalarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/salarios")

public class SalarioController {

    private final SalarioService service;

    @PostMapping
    public ResponseEntity<SalarioResponseDTO> criarSalario(@RequestBody @Valid SalarioRequestDTO requestDTO){
        SalarioResponseDTO response =  service.criarSalario(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SalarioResponseDTO>> obterSalarios() {
        return ResponseEntity.ok(service.buscarSalarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalarioResponseDTO> obterSalarioPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalario(@PathVariable Long id) {
        service.deletarSalario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalarioResponseDTO> atualizarSalario(@PathVariable Long id, @RequestBody @Valid SalarioRequestDTO requestDTO) {
        SalarioResponseDTO response = service.atualizarSalario(id, requestDTO);
        return ResponseEntity.ok(response);
    }
}
