package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ComentarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ComentarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.ComentarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid ComentarioCriadoDTO dto){
        service.salvar(dto);
    }

    @GetMapping("/{id}")
    public ComentarioRetornoDTO buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<ComentarioRetornoDTO> listar(){
        return service.listarTodas();
    }

}
