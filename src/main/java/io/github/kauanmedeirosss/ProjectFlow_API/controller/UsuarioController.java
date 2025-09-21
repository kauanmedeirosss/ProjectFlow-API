package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid UsuarioCriadoDTO dto){
        service.salvar(dto);
    }

    @GetMapping("/{id}")
    public UsuarioRetornoDTO buscar(@PathVariable Long id){
       return service.buscarPorId(id);
    }

    @GetMapping
    public List<UsuarioRetornoDTO> listar(){
        return service.listarTodos();
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid UsuarioAtualizadoDTO dto){
        service.atualizar(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }

}
