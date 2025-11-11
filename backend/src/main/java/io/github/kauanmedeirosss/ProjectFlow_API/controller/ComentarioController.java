package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
@Tag(name = "Comentários", description = "Operações de gerenciamento de comentários")
public class ComentarioController {

    private final ComentarioService service;

    @Operation(summary = "Criar novo comentário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Comentário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Tarefa ou usuário não encontrados")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<ComentarioRetornoDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do comentário a ser criado",
            required = true)
            @RequestBody @Valid ComentarioCriadoDTO dto, UriComponentsBuilder uriBuilder){
        var comentario = service.salvar(dto);
        var uri = uriBuilder.path("/comentarios/{id}").buildAndExpand(comentario.id()).toUri();
        return ResponseEntity.created(uri).body(comentario);
    }

    @Operation(summary = "Buscar comentário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário encontrado"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ComentarioRetornoDTO> buscar(@PathVariable Long id){
        var comentario = service.buscarPorId(id);
        return ResponseEntity.ok(comentario);
    }

    @Operation(
            summary = "Listar todos os comentários",
            description = "Retorna uma lista de todos os comentários cadastrados no sistema"
    )
    @ApiResponse(responseCode = "200", description = "Comentários listados com sucesso")
    @GetMapping
    public ResponseEntity<List<ComentarioRetornoDTO>> listar(){
        var comentarios = service.listarTodas();
        return ResponseEntity.ok(comentarios);
    }


    @Operation(
            summary = "Atualizar um comentário",
            description = "Atualiza um comentário existente passando seu ID no body da requisição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado"),
    })
    @PutMapping
    @Transactional
    public ResponseEntity<ComentarioRetornoDTO> atualizar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do comentário a ser atualizado",
            required = true)
            @RequestBody @Valid ComentarioAtualizadoDTO dto){
        var comentario = service.atualizar(dto);
        return ResponseEntity.ok(comentario);
    }

    @Operation(summary = "Deletar comentário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário deletado"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
