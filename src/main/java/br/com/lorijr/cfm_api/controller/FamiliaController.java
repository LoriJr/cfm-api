package br.com.lorijr.cfm_api.controller;

import br.com.lorijr.cfm_api.dto.familia.FamiliaRequestDTO;
import br.com.lorijr.cfm_api.dto.familia.FamiliaResponseDTO;
import br.com.lorijr.cfm_api.service.FamiliaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/familias")
public class FamiliaController {

    private final FamiliaService service;

    @PostMapping
    public ResponseEntity<FamiliaResponseDTO> criarFamilia(@RequestBody FamiliaRequestDTO requestDTO){
        FamiliaResponseDTO familia = service.salvarFamilia(requestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(familia)
                .toUri();
        return ResponseEntity.created(uri).body(familia);
    }

    @GetMapping
    public ResponseEntity<List<FamiliaResponseDTO>> listarFamilias() {
        return ResponseEntity.ok(service.buscarFamilia());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamiliaResponseDTO> listarPorId(@PathVariable Long id) {
        FamiliaResponseDTO response = service.buscarPorId(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFamilia(@PathVariable Long id) {
        service.deletarFamilia(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FamiliaResponseDTO> atualizarFamilia(@PathVariable Long id, @RequestBody FamiliaRequestDTO requestDTO) {
        FamiliaResponseDTO response = service.atualizarFamilia(id, requestDTO);
        return ResponseEntity.ok().body(response);
    }


}
