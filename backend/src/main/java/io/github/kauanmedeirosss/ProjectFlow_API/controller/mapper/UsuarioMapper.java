package io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.usuario.UsuarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.usuario.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioCriadoDTO dto);

    UsuarioRetornoDTO toRetornoDTO(Usuario usuario);

}
