package io.github.kauanmedeirosss.ProjectFlow_API.controller;

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

    /*
    alguma forma de adicionar tarefas ao projeto (acho que no usuario)

    avançar o status (só avança pra frente)

    put de orçamento, ja que pode receber nulo
    */

}
