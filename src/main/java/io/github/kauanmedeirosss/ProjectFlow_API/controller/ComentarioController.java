package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ComentarioAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ComentarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ComentarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.ComentarioService;
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
public class ComentarioController {

    private final ComentarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity<ComentarioRetornoDTO> cadastrar(@RequestBody @Valid ComentarioCriadoDTO dto, UriComponentsBuilder uriBuilder){
        var comentario = service.salvar(dto);
        var uri = uriBuilder.path("/comentarios/{id}").buildAndExpand(comentario.id()).toUri();
        return ResponseEntity.created(uri).body(comentario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioRetornoDTO> buscar(@PathVariable Long id){
        var comentario = service.buscarPorId(id);
        return ResponseEntity.ok(comentario);
    }

    @GetMapping
    public ResponseEntity<List<ComentarioRetornoDTO>> listar(){
        var comentarios = service.listarTodas();
        return ResponseEntity.ok(comentarios);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ComentarioRetornoDTO> atualizar(@RequestBody @Valid ComentarioAtualizadoDTO dto){
        var comentario = service.atualizar(dto);
        return ResponseEntity.ok(comentario);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
