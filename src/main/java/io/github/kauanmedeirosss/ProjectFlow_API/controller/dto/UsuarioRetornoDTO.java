package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Role;

import java.util.List;

public record UsuarioRetornoDTO(
        Long id,
        String nome,
        String email,
        Role role
) {
}
