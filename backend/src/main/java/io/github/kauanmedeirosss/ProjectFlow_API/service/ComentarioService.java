package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.ComentarioMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.ComentarioRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.TarefaRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository repository;
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioMapper mapper;

    public ComentarioRetornoDTO salvar(ComentarioCriadoDTO dto){
        var comentario = mapper.toEntity(dto);

        var tarefa = tarefaRepository.findById(dto.tarefa_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
        var usuario = usuarioRepository.findById(dto.autor_id())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        comentario.setTarefa(tarefa);
        comentario.setAutor(usuario);
        comentario.setCriadoEm(LocalDateTime.now());

        repository.save(comentario);
        return mapper.toRetornoDTO(comentario);
    }

    public ComentarioRetornoDTO buscarPorId(Long id){
        var comentario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado!"));
        return mapper.toRetornoDTO(comentario);
    }

    public List<ComentarioRetornoDTO> listarTodas(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

    public ComentarioRetornoDTO atualizar(ComentarioAtualizadoDTO dto){
        var comentario = repository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado!"));
        comentario.setConteudo(dto.conteudo());
        repository.save(comentario);
        return mapper.toRetornoDTO(comentario);
    }

    public void deletar(Long id){
        var comentario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado!"));
        repository.delete(comentario);
    }

}
