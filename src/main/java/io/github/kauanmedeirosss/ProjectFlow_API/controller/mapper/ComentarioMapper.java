package io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ComentarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ComentarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(target = "id", ignore = true)
    Comentario toEntity(ComentarioCriadoDTO dto);

    ComentarioRetornoDTO toRetornoDTO(Comentario comentario);

}
