package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.usuario;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Role;

public record UsuarioRetornoDTO(
        Long id,
        String nome,
        String email,
        Role role
) {
}
