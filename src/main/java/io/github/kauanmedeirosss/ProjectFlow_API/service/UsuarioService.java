package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.UsuarioMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public void salvar(UsuarioCriadoDTO dto) {
        var usuario = mapper.toEntity(dto);
        repository.save(usuario);
    }

    public UsuarioRetornoDTO buscarPorId(Long id){
        var usuario = repository.getReferenceById(id);
        return mapper.toRetornoDTO(usuario);
    }

    public List<UsuarioRetornoDTO> listarTodos(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

}
