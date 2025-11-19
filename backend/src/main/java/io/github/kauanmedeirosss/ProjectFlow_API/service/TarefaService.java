package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.PaginaResponseDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaAtualizadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaAtualizadaStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.AnexoMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.ComentarioMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.TarefaMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Anexo;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Comentario;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper mapper;
    private final AnexoMapper anexoMapper;
    private final ComentarioMapper comentarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final AnexoRepository anexoRepository;
    private final ComentarioRepository comentarioRepository;

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

    public PaginaResponseDTO<TarefaRetornoDTO> listarTodas(Pageable pageable) {
        Page<Tarefa> pagina = repository.findAll(pageable);

        List<TarefaRetornoDTO> conteudo = pagina.getContent()
                .stream()
                .map(mapper::toRetornoDTO)
                .toList();

        return PaginaResponseDTO.of(
                conteudo,
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements(),
                pagina.getTotalPages()
        );
    }

    public PaginaResponseDTO<AnexoRetornoDTO> listarAnexosDaTarefaId(Long id, Pageable pageable) {
        Page<Anexo> pagina = anexoRepository.findByTarefaIdPaginado(id, pageable);

        List<AnexoRetornoDTO> conteudo = pagina.getContent()
                .stream()
                .map(anexoMapper::toRetornoDTO)
                .collect(Collectors.toList());

        return PaginaResponseDTO.of(
                conteudo,
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements(),
                pagina.getTotalPages()
        );
    }

    public PaginaResponseDTO<ComentarioRetornoDTO> listarComentariosDaTarefaId(Long id, Pageable pageable) {
        Page<Comentario> pagina = comentarioRepository.findByTarefaIdPaginado(id, pageable);

        List<ComentarioRetornoDTO> conteudo = pagina.getContent()
                .stream()
                .map(comentarioMapper::toRetornoDTO)
                .collect(Collectors.toList());

        return PaginaResponseDTO.of(
                conteudo,
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements(),
                pagina.getTotalPages()
        );
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
