package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaAtualizadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaAtualizadaStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.TarefaService;
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
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Operações de gerenciamento de tarefas")
public class TarefaController {

    private final TarefaService service;

    @Operation(summary = "Criar nova tarefa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Projeto ou usuário não encontrados")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<TarefaRetornoDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da tarefa a ser criada",
            required = true)
            @RequestBody @Valid TarefaCriadaDTO dto, UriComponentsBuilder uriBuilder){
        var tarefa = service.salvar(dto);
        var uri = uriBuilder.path("/tarefas/{id}").buildAndExpand(tarefa.id()).toUri();
        return ResponseEntity.created(uri).body(tarefa);
    }

    @Operation(summary = "Buscar tarefa por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TarefaRetornoDTO> buscar(@PathVariable Long id){
        var tarefa = service.buscarPorId(id);
        return ResponseEntity.ok(tarefa);
    }

    @Operation(
            summary = "Listar todas as tarefas",
            description = "Retorna uma lista de todas as tarefas cadastradas no sistema"
    )
    @ApiResponse(responseCode = "200", description = "Tarefas listadas com sucesso")
    @GetMapping
    public ResponseEntity<List<TarefaRetornoDTO>> listar(){
        var tarefas = service.listarTodas();
        return ResponseEntity.ok(tarefas);
    }

    @Operation(
            summary = "Atualizar uma tarefa",
            description = "Atualiza uma tarefa existente passando seu ID no body da requisição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
    })
    @PutMapping
    @Transactional
    public ResponseEntity<TarefaRetornoDTO> atualizar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da tarefa a ser atualizada",
            required = true)
            @RequestBody @Valid TarefaAtualizadaDTO dto){
        var tarefa = service.atualizar(dto);
        return ResponseEntity.ok(tarefa);
    }

    @Operation(
            summary = "Atualizar status de uma tarefa",
            description = "Atualiza uma tarefa existente passando seu ID e o status desejado no body da requisição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @PutMapping("/{id}/status")
    @Transactional
    public ResponseEntity<Void> atualizarStatus(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da tarefa a ter status atualizado",
            required = true)
             @PathVariable Long id, @RequestBody @Valid TarefaAtualizadaStatusDTO dto){
        service.atualizarStatus(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar tarefa por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa deletada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar anexos de uma tarefa por ID da tarefa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anexos listados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @GetMapping("/{id}/anexos")
    public ResponseEntity<List<AnexoRetornoDTO>> listarAnexosTarefa(@PathVariable Long id){
        var anexos = service.listarAnexosDaTarefaId(id);
        return ResponseEntity.ok(anexos);
    }

}
