package io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoProgressoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Projeto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjetoMapper {

    @Mapping(target = "id", ignore = true)
    Projeto toEntity(ProjetoCriadoDTO dto);

    ProjetoRetornoDTO toRetornoDTO(Projeto projeto);

    ProjetoProgressoDTO toProgressoDTO(Projeto projeto);

}
