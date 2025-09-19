package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.AnexoMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.AnexoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnexoService {


    private final AnexoRepository repository;
    private final AnexoMapper mapper;
    private final TarefaService tarefaService;

    public void salvar(AnexoCriadoDTO dto) {
        var anexo = mapper.toEntity(dto);

        var tarefa = tarefaService.obterPorId(dto.getTarefaId());
        if (tarefa == null) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

        anexo.setUploadEm(LocalDateTime.now());
        repository.save(anexo);
    }


}
