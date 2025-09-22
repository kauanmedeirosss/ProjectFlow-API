package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioRetornoDTO> cadastrar(@RequestBody @Valid UsuarioCriadoDTO dto, UriComponentsBuilder uriBuilder){
        var usuario = service.salvar(dto);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRetornoDTO> buscar(@PathVariable Long id){
       var usuario = service.buscarPorId(id);
       return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRetornoDTO>> listar(){
        var usuarios = service.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UsuarioRetornoDTO> atualizar(@RequestBody @Valid UsuarioAtualizadoDTO dto){
        var usuario = service.atualizar(dto);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
