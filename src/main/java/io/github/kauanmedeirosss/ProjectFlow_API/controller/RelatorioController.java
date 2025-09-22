package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoProgressoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;

    @GetMapping("/progresso-projetos")
    public ResponseEntity<List<ProjetoProgressoDTO>> progressoProjeto(){
        var relatorio = service.listarProgressosProjetos();
        return ResponseEntity.ok(relatorio);
    }

}
