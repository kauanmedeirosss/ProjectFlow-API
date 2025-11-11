package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.BusinessRuleException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.UsuarioMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Usuario;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder encoder;

    public UsuarioRetornoDTO salvar(UsuarioCriadoDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new BusinessRuleException("Email já cadastrado");
        }
        var usuario = mapper.toEntity(dto);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);
        return mapper.toRetornoDTO(usuario);
    }

    public UsuarioRetornoDTO buscarPorId(Long id){
        var usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        return mapper.toRetornoDTO(usuario);
    }

    public List<UsuarioRetornoDTO> listarTodos(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

    public Usuario retornaEntidade(Long id){
        var usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        return usuario;
    }

    public UsuarioRetornoDTO atualizar(UsuarioAtualizadoDTO dto){
        var usuario = repository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        repository.save(usuario);
        return mapper.toRetornoDTO(usuario);
    }

    public void deletar(Long id){
        var usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        repository.delete(usuario);
    }

}
