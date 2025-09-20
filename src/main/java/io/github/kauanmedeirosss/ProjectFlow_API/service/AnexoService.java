package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.AnexoMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.AnexoRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnexoService {


    private final AnexoRepository repository;
    private final AnexoMapper mapper;
    private final TarefaRepository tarefaRepository;

    public void salvar(AnexoCriadoDTO dto) {
        var anexo = mapper.toEntity(dto);

        var tarefa = tarefaRepository.findById(dto.tarefa_id())
                        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));

        anexo.setTarefa(tarefa);
        anexo.setUploadEm(LocalDateTime.now());
        repository.save(anexo);
    }

    public AnexoRetornoDTO buscarPorId(Long id){
        var anexo = repository.getReferenceById(id);
        return mapper.toRetornoDTO(anexo);
    }

    public List<AnexoRetornoDTO> listarTodas(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

}
