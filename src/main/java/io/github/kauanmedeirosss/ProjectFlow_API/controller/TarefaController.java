package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.*;
import io.github.kauanmedeirosss.ProjectFlow_API.service.TarefaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid TarefaCriadaDTO dto){
        service.salvar(dto);
    }

    @GetMapping("/{id}")
    public TarefaRetornoDTO buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<TarefaRetornoDTO> listar(){
        return service.listarTodas();
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid TarefaAtualizadaDTO dto){
        service.atualizar(dto);
    }

    @PutMapping("/{id}/status")
    @Transactional
    public void atualizarStatus(@RequestBody @PathVariable @Valid Long id, TarefaAtualizadaStatusDTO dto){
        service.atualizarStatus(id, dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }

    @GetMapping("/{id}/anexos")
    public List<AnexoRetornoDTO> listarAnexosTarefa(@PathVariable Long id){
        return service.listarAnexosDaTarefaId(id);
    }

}
