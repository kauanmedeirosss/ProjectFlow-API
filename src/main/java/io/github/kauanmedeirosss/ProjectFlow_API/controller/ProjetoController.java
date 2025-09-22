package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoAtualizadoStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.ProjetoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<ProjetoRetornoDTO> cadastrar(@RequestBody @Valid ProjetoCriadoDTO dto, UriComponentsBuilder uriBuilder){
        var projeto = service.salvar(dto);
        var uri = uriBuilder.path("/projetos/{id}").buildAndExpand(projeto.id()).toUri();
        return ResponseEntity.created(uri).body(projeto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoRetornoDTO> buscar(@PathVariable Long id){
        var projeto = service.buscarPorId(id);
        return ResponseEntity.ok(projeto);
    }

    @GetMapping
    public ResponseEntity<List<ProjetoRetornoDTO>> listar(){
        var projetos = service.listarTodas();
        return ResponseEntity.ok(projetos);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ProjetoRetornoDTO> atualizar(@RequestBody @Valid ProjetoAtualizadoDTO dto){
        var projeto = service.atualizar(dto);
        return ResponseEntity.ok(projeto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    @Transactional
    public ResponseEntity<Void> atualizarStatus(@RequestBody @PathVariable @Valid Long id, ProjetoAtualizadoStatusDTO dto){
        service.atualizarStatus(id, dto);
        return ResponseEntity.noContent().build();
    }

}
