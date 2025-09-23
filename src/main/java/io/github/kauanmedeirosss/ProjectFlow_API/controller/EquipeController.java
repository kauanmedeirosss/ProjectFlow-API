package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeAtualizadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.EquipeService;
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
public class EquipeController {

    private final EquipeService service;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<EquipeRetornoDTO> cadastrar(@RequestBody @Valid EquipeCriadaDTO dto, UriComponentsBuilder uriBuilder){
        var equipe = service.salvar(dto);
        var uri = uriBuilder.path("/equipes/{id}").buildAndExpand(equipe.id()).toUri();
        return ResponseEntity.created(uri).body(equipe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeRetornoDTO> buscar(@PathVariable Long id){
        var equipe = service.buscarPorId(id);
        return ResponseEntity.ok(equipe);
    }

    @GetMapping
    public ResponseEntity<List<EquipeRetornoDTO>> listar(){
        var equipes = service.listarTodas();
        return ResponseEntity.ok(equipes);
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<EquipeRetornoDTO> atualizar(@RequestBody @Valid EquipeAtualizadaDTO dto){
        var equipe = service.atualizar(dto);
        return ResponseEntity.ok(equipe);
    }

    @PostMapping("/{equipeId}/membros/{usuarioaId}")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> adicionarMembro(@PathVariable Long equipeId,
                                @PathVariable Long usuarioId){
        service.adicionarMembro(equipeId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{equipeId}/membros/{usuarioaId}")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> removerMembro(@PathVariable Long equipeId,
                              @PathVariable Long usuarioId){
        service.removerMembro(equipeId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{equipeId}/membros")
    public ResponseEntity<List<UsuarioRetornoDTO>> listarMembros(
            @PathVariable Long equipeId) {
        var membros = service.listarMembros(equipeId);
        return ResponseEntity.ok(membros);
    }

}
