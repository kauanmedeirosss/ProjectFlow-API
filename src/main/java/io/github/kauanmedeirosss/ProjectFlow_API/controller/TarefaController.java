package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.*;
import io.github.kauanmedeirosss.ProjectFlow_API.service.TarefaService;
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
public class TarefaController {

    private final TarefaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<TarefaRetornoDTO> cadastrar(@RequestBody @Valid TarefaCriadaDTO dto, UriComponentsBuilder uriBuilder){
        var tarefa = service.salvar(dto);
        var uri = uriBuilder.path("/tarefas/{id}").buildAndExpand(tarefa.id()).toUri();
        return ResponseEntity.created(uri).body(tarefa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaRetornoDTO> buscar(@PathVariable Long id){
        var tarefa = service.buscarPorId(id);
        return ResponseEntity.ok(tarefa);
    }

    @GetMapping
    public ResponseEntity<List<TarefaRetornoDTO>> listar(){
        var tarefas = service.listarTodas();
        return ResponseEntity.ok(tarefas);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TarefaRetornoDTO> atualizar(@RequestBody @Valid TarefaAtualizadaDTO dto){
        var tarefa = service.atualizar(dto);
        return ResponseEntity.ok(tarefa);
    }

    @PutMapping("/{id}/status")
    @Transactional
    public ResponseEntity<Void> atualizarStatus(@RequestBody @PathVariable @Valid Long id, TarefaAtualizadaStatusDTO dto){
        service.atualizarStatus(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/anexos")
    public ResponseEntity<List<AnexoRetornoDTO>> listarAnexosTarefa(@PathVariable Long id){
        var anexos = service.listarAnexosDaTarefaId(id);
        return ResponseEntity.ok(anexos);
    }

}
