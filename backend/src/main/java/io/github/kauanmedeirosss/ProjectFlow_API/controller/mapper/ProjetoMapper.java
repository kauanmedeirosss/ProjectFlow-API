package io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoMembroDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoProgressoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Projeto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjetoMapper {

    @Mapping(target = "id", ignore = true)
    Projeto toEntity(ProjetoCriadoDTO dto);

    @Mapping(target = "nomeEquipe", source = "equipe.nome")
    ProjetoRetornoDTO toRetornoDTO(Projeto projeto);

    ProjetoProgressoDTO toProgressoDTO(Projeto projeto);

    @Mapping(target = "status", expression = "java(projeto.getStatus().name())")
    @Mapping(target = "equipeNome", source = "equipe.nome")
    ProjetoMembroDTO toProjetoMembroDTO(Projeto projeto);

}
