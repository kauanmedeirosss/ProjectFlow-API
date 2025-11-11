package io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Projeto;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Usuario;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.ProjetoRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cessionario", expression = "java(mapCessionario(dto.getCessionario(), usuarioRepository))")
    @Mapping(target = "projeto", expression = "java(mapProjeto(dto.getProjeto_id(), projetoRepository))")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "anexos", ignore = true)
    Tarefa toEntity(TarefaCriadaDTO dto,
                    @Context UsuarioRepository usuarioRepository,
                    @Context ProjetoRepository projetoRepository);

    TarefaRetornoDTO toRetornoDTO(Tarefa tarefa);

    default Usuario mapCessionario(Long cessionarioId, @Context UsuarioRepository usuarioRepository) {
        if (cessionarioId == null) return null;
        return usuarioRepository.findById(cessionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    default Projeto mapProjeto(Long projetoId, @Context ProjetoRepository projetoRepository) {
        if (projetoId == null) return null;
        return projetoRepository.findById(projetoId)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
    }

}
