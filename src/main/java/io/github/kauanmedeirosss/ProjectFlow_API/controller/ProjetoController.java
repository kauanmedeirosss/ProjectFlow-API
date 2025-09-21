package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoAtualizadoStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.ProjetoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid ProjetoCriadoDTO dto){
        service.salvar(dto);
    }

    @GetMapping("/{id}")
    public ProjetoRetornoDTO buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<ProjetoRetornoDTO> listar(){
        return service.listarTodas();
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid ProjetoAtualizadoDTO dto){
        service.atualizar(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public void atualizarStatus(@RequestBody @PathVariable @Valid Long id, ProjetoAtualizadoStatusDTO dto){
        service.atualizarStatus(id, dto);
    }

}
