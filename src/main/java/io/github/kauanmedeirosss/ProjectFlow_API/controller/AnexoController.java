package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.AnexoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/anexos")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
public class AnexoController {

    private final AnexoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<AnexoRetornoDTO> cadastrar(@RequestBody @Valid AnexoCriadoDTO dto, UriComponentsBuilder uriBuilder){
        var anexo = service.salvar(dto);
        var uri = uriBuilder.path("/anexos/{id}").buildAndExpand(anexo.id()).toUri();
        return ResponseEntity.created(uri).body(anexo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnexoRetornoDTO> buscar(@PathVariable Long id){
        var anexo = service.buscarPorId(id);
        return ResponseEntity.ok(anexo);
    }

    @GetMapping
    public ResponseEntity<List<AnexoRetornoDTO>> listar(){
        var anexos = service.listarTodas();
        return ResponseEntity.ok(anexos);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AnexoRetornoDTO> atualizar(@RequestBody @Valid AnexoAtualizadoDTO dto){
        var anexo = service.atualizar(dto);
        return ResponseEntity.ok(anexo);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}