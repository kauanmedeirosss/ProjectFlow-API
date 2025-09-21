package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioAtualizadoDTO(
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        @NotBlank(message = "Campo obrigatório!")
        @Email(message = "Campo email preenchido incorretamente.")
        String email
) {
}
