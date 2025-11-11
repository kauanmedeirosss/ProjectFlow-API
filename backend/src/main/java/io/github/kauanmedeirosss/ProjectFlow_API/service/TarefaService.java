package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaAtualizadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaAtualizadaStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.AnexoMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.TarefaMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.AnexoRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.ProjetoRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.TarefaRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper mapper;
    private final AnexoMapper anexoMapper;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final AnexoRepository anexoRepository;

    public Tarefa obterPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
    }

    public TarefaRetornoDTO salvar(TarefaCriadaDTO dto){
        var tarefa = mapper.toEntity(dto, usuarioRepository, projetoRepository);

        var projeto = projetoRepository.findById(dto.projeto_id())
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));
        var usuario = usuarioRepository.findById(dto.cessionario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        tarefa.setProjeto(projeto);
        tarefa.setCessionario(usuario);
        tarefa.setStatus(StatusTarefa.valueOf("A_FAZER"));

        repository.save(tarefa);
        return mapper.toRetornoDTO(tarefa);
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

    public List<AnexoRetornoDTO> listarAnexosDaTarefaId(Long id){
        var tarefa = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
        var lista = anexoRepository.findByTarefaId(id);
        return lista.stream()
                .map(anexoMapper::toRetornoDTO)
                .collect(Collectors.toList());
    }

    public TarefaRetornoDTO atualizar(TarefaAtualizadaDTO dto){
        var tarefa = repository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setHorasEstimadas(dto.horasEstimadas());
        repository.save(tarefa);
        return mapper.toRetornoDTO(tarefa);
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
