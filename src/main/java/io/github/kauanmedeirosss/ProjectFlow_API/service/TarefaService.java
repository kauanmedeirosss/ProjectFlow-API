package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.TarefaCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.TarefaRetornoDTO;
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
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public void salvar(TarefaCriadaDTO dto){
        var tarefa = mapper.toEntity(dto, usuarioRepository, projetoRepository);

        var projeto = projetoRepository.findById(dto.projeto_id())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));
        var usuario = usuarioRepository.findById(dto.cessionario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        tarefa.setProjeto(projeto);
        tarefa.setCessionario(usuario);
        tarefa.setStatus(StatusTarefa.valueOf("A_FAZER"));

        repository.save(tarefa);
    }

    public TarefaRetornoDTO buscarPorId(Long id){
        var equipe = repository.getReferenceById(id);
        return mapper.toRetornoDTO(equipe);
    }

    public List<TarefaRetornoDTO> listarTodas(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

}
