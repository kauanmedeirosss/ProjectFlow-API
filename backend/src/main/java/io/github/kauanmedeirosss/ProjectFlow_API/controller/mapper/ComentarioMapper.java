package io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario.ComentarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Comentario;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(target = "id", ignore = true)
    Comentario toEntity(ComentarioCriadoDTO dto);

    ComentarioRetornoDTO toRetornoDTO(Comentario comentario);

    default String mapUsuarioToString(Usuario usuario) {
        return usuario != null ? usuario.getNome() : null;
    }
}
