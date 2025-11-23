package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoProgressoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
public class RelatorioController {

    private final RelatorioService service;

    @GetMapping("/progresso")
    public ResponseEntity<List<ProjetoProgressoDTO>> progressoProjeto() {
        return ResponseEntity.ok(service.listarProgressosProjetos());
    }
}
