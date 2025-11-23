package br.com.lorijr.cfm_api.controller;

import br.com.lorijr.cfm_api.dto.fatura.FaturaMensalResponseDTO;
import br.com.lorijr.cfm_api.service.FaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("faturas")
public class FaturaController {

    private final FaturaService service;

    @GetMapping
    public ResponseEntity<FaturaMensalResponseDTO> gerar(
            @RequestParam Long familiaId,
            @RequestParam int mes,
            @RequestParam int ano
    ) {
        return ResponseEntity.ok(service.gerarFatura(familiaId, mes, ano));
    }
}
