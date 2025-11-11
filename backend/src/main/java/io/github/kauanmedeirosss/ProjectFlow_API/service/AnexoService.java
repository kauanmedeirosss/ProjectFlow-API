package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.PaginaResponseDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.AnexoMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Anexo;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.AnexoRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnexoService {


    private final AnexoRepository repository;
    private final AnexoMapper mapper;
    private final TarefaRepository tarefaRepository;

    public AnexoRetornoDTO salvar(AnexoCriadoDTO dto) {
        var anexo = mapper.toEntity(dto);

        var tarefa = tarefaRepository.findById(dto.tarefa_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        anexo.setTarefa(tarefa);
        anexo.setUploadEm(LocalDateTime.now());
        repository.save(anexo);

        return mapper.toRetornoDTO(anexo);
    }

    public AnexoRetornoDTO buscarPorId(Long id){
        var anexo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anexo não encontrado!"));
        return mapper.toRetornoDTO(anexo);
    }

    public PaginaResponseDTO<AnexoRetornoDTO> listarTodas(Pageable pageable) {
        Page<Anexo> pagina = repository.findAll(pageable);

        List<AnexoRetornoDTO> conteudo = pagina.getContent()
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

    public AnexoRetornoDTO atualizar(AnexoAtualizadoDTO dto){
        var anexo = repository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Anexo não encontrado!"));
        anexo.setNomeArquivo(dto.nomeArquivo());
        anexo.setURLarquivo(dto.URLarquivo());
        repository.save(anexo);

        return mapper.toRetornoDTO(anexo);
    }

    public void deletar(Long id){
        var anexo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anexo não encontrado!"));
        repository.delete(anexo);
    }

}
