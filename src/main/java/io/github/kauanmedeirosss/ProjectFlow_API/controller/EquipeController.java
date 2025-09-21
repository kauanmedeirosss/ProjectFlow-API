package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeAtualizadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.EquipeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipes")
@RequiredArgsConstructor
public class EquipeController {

    private final EquipeService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid EquipeCriadaDTO dto){
        service.salvar(dto);
    }

    @GetMapping("/{id}")
    public EquipeRetornoDTO buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<EquipeRetornoDTO> listar(){
        return service.listarTodas();
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid EquipeAtualizadaDTO dto){
        service.atualizar(dto);
    }

    @PostMapping("/{equipeId}/membros/{usuarioaId}")
    @Transactional
    public void adicionarMembro(@PathVariable Long equipeId,
                                @PathVariable Long usuarioId){
        service.adicionarMembro(equipeId, usuarioId);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }

    @DeleteMapping("/{equipeId}/membros/{usuarioaId}")
    @Transactional
    public void removerMembro(@PathVariable Long equipeId,
                              @PathVariable Long usuarioId){
        service.removerMembro(equipeId, usuarioId);
    }

    @GetMapping("/{equipeId}/membros")
    public List<UsuarioRetornoDTO> listarMembros(
            @PathVariable Long equipeId) {
        return service.listarMembros(equipeId);
    }

}
