package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoProgressoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
@Tag(name = "Relatórios", description = "Obtenção de relatórios")
public class RelatorioController {

    private final RelatorioService service;

    @Operation(
            summary = "Busca progressos dos projetos",
            description = "Retorna progressos de projetos cadastrados no sistema"
    )
    @ApiResponse(responseCode = "200", description = "Progressos obtidos com sucesso")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @GetMapping("/progresso")
    public ResponseEntity<List<ProjetoProgressoDTO>> progressoProjeto(){
        var relatorio = service.listarProgressosProjetos();
        return ResponseEntity.ok(relatorio);
    }

}
