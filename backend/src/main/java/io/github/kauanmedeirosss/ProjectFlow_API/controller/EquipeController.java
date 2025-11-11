package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeAtualizadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.EquipeService;
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
@RequestMapping("/equipes")
@RequiredArgsConstructor
@Tag(name = "Equipes", description = "Operações de gerenciamento de equipes")
public class EquipeController {

    private final EquipeService service;

    @Operation(summary = "Criar nova equipe")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Equipe criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<EquipeRetornoDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da equipe a ser criada",
            required = true)
            @RequestBody @Valid EquipeCriadaDTO dto, UriComponentsBuilder uriBuilder){
        var equipe = service.salvar(dto);
        var uri = uriBuilder.path("/equipes/{id}").buildAndExpand(equipe.id()).toUri();
        return ResponseEntity.created(uri).body(equipe);
    }

    @Operation(summary = "Buscar equipe por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipe encontrada"),
            @ApiResponse(responseCode = "404", description = "Equipe não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EquipeRetornoDTO> buscar(@PathVariable Long id){
        var equipe = service.buscarPorId(id);
        return ResponseEntity.ok(equipe);
    }

    @Operation(
            summary = "Listar todas as equipes",
            description = "Retorna uma lista de todas as equipes cadastradas no sistema"
    )
    @ApiResponse(responseCode = "200", description = "Equipes listadas com sucesso")
    @GetMapping
    public ResponseEntity<List<EquipeRetornoDTO>> listar(){
        var equipes = service.listarTodas();
        return ResponseEntity.ok(equipes);
    }

    @Operation(
            summary = "Atualizar uma equipe",
            description = "Atualiza uma equipe existente passando seu ID no body da requisição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipe atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Equipe não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<EquipeRetornoDTO> atualizar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da equipe a ser atualizada",
            required = true)
            @RequestBody @Valid EquipeAtualizadaDTO dto){
        var equipe = service.atualizar(dto);
        return ResponseEntity.ok(equipe);
    }

    @Operation(summary = "Adicionar membro a uma equipe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description="Membro adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário já é membro desta equipe"),
            @ApiResponse(responseCode = "404", description = "Equipe ou membro não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @PostMapping("/{equipeId}/membros/{usuarioId}")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> adicionarMembro(@PathVariable Long equipeId,
                                @PathVariable Long usuarioId){
        service.adicionarMembro(equipeId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar projeto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipe deletada"),
            @ApiResponse(responseCode = "404", description = "Equipe não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover membro de uma equipe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description="Membro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipe ou membro não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @DeleteMapping("/{equipeId}/membros/{usuarioId}")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> removerMembro(@PathVariable Long equipeId,
                              @PathVariable Long usuarioId){
        service.removerMembro(equipeId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar membros de uma equipe por ID da equipe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membros listados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipe não encontrada")
    })
    @GetMapping("/{equipeId}/membros")
    public ResponseEntity<List<UsuarioRetornoDTO>> listarMembros(
            @PathVariable Long equipeId) {
        var membros = service.listarMembros(equipeId);
        return ResponseEntity.ok(membros);
    }

}
