package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.AnexoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anexos")
@RequiredArgsConstructor
public class AnexoController {

    private final AnexoService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid AnexoCriadoDTO dto){
        service.salvar(dto);
    }

    @GetMapping("/{id}")
    public AnexoRetornoDTO buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<AnexoRetornoDTO> listar(){
        return service.listarTodas();
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid AnexoAtualizadoDTO dto){
        service.atualizar(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }

}
