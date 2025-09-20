package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeRetornoDTO;
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

    /*
    PUT
    membros

    PUT
    projeto
    */

}
