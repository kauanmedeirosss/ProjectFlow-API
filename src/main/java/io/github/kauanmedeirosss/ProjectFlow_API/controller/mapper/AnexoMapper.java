package io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Anexo;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnexoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tarefa", expression = "java(mapTarefaId(dto.getTarefaId()))")
    Anexo toEntity(AnexoCriadoDTO dto);

    AnexoRetornoDTO toRetornoDTO(Anexo anexo);

    default Tarefa mapTarefaId(Long tarefaId) {
        if (tarefaId == null) return null;
        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        return tarefa;
    }

}
