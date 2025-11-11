package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoAtualizadoStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
@Tag(name = "Projetos", description = "Operações de gerenciamento de projetos")
public class ProjetoController {

    private final ProjetoService service;

    @Operation(summary = "Criar novo projeto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Equipe não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<ProjetoRetornoDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do projeto a ser criado",
            required = true)
            @RequestBody @Valid ProjetoCriadoDTO dto, UriComponentsBuilder uriBuilder){
        var projeto = service.salvar(dto);
        var uri = uriBuilder.path("/projetos/{id}").buildAndExpand(projeto.id()).toUri();
        return ResponseEntity.created(uri).body(projeto);
    }

    @Operation(summary = "Buscar projeto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoRetornoDTO> buscar(@PathVariable Long id){
        var projeto = service.buscarPorId(id);
        return ResponseEntity.ok(projeto);
    }

    @Operation(
            summary = "Listar todos os projetos",
            description = "Retorna uma lista de todos os projetos cadastrados no sistema"
    )
    @ApiResponse(responseCode = "200", description = "Projetos listados com sucesso")
    @GetMapping
    public ResponseEntity<List<ProjetoRetornoDTO>> listar(){
        var projetos = service.listarTodas();
        return ResponseEntity.ok(projetos);
    }

    @Operation(
            summary = "Atualizar um projeto",
            description = "Atualiza um projeto existente passando seu ID no body da requisição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<ProjetoRetornoDTO> atualizar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do projeto a ser atualizado",
            required = true)
            @RequestBody @Valid ProjetoAtualizadoDTO dto){
        var projeto = service.atualizar(dto);
        return ResponseEntity.ok(projeto);
    }

    @Operation(summary = "Deletar projeto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto deletado"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Atualizar status de um projeto",
            description = "Atualiza um projeto existente passando seu ID e o status desejado no body da requisição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @PutMapping("/{id}/status")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> atualizarStatus(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do projeto a ter status atualizado",
            required = true)
            @PathVariable Long id, @RequestBody @Valid ProjetoAtualizadoStatusDTO dto){
        service.atualizarStatus(id, dto);
        return ResponseEntity.noContent().build();
    }

}
