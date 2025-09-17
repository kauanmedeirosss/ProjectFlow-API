package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Role;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioCriadoDTO(
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        @NotBlank(message = "Campo obrigatório!")
        @Email(message = "Campo email preenchido incorretamente.")
        String email,
        @NotBlank(message = "Campo obrigatório!")
        String senha,
        @NotNull(message = "Campo obrigatório")
        @Enumerated
        Role role
) {
}
