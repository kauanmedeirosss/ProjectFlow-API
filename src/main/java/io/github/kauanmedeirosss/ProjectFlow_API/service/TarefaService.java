package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.TarefaAtualizadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.TarefaAtualizadaStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.TarefaCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.TarefaRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.TarefaMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.ProjetoRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.TarefaRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper mapper;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;

    public Tarefa obterPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
    }

    public void salvar(TarefaCriadaDTO dto){
        var tarefa = mapper.toEntity(dto, usuarioRepository, projetoRepository);

        var projeto = projetoRepository.findById(dto.projeto_id())
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));
        var usuario = usuarioRepository.findById(dto.cessionario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        tarefa.setProjeto(projeto);
        tarefa.setCessionario(usuario);
        tarefa.setStatus(StatusTarefa.valueOf("A_FAZER"));

        repository.save(tarefa);
    }

    public TarefaRetornoDTO buscarPorId(Long id){
        var tarefa = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
        return mapper.toRetornoDTO(tarefa);
    }

    public List<TarefaRetornoDTO> listarTodas(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

    public void atualizar(TarefaAtualizadaDTO dto){
        var tarefa = repository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setHorasEstimadas(dto.horasEstimadas());
        repository.save(tarefa);
    }

    public void atualizarStatus(Long id, TarefaAtualizadaStatusDTO dto){
        var tarefa = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
        tarefa.setStatus(dto.status());
        repository.save(tarefa);
    }

    public void deletar(Long id){
        var tarefa = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
        repository.delete(tarefa);
    }

}
