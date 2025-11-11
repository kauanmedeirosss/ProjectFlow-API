package io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.equipe.EquipeCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.equipe.EquipeRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Equipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EquipeMapper {

    @Mapping(target = "id", ignore = true)
    Equipe toEntity(EquipeCriadaDTO dto);

    EquipeRetornoDTO toRetornoDTO(Equipe equipe);

}
