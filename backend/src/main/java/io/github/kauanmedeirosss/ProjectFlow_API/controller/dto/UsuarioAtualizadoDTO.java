package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioAtualizadoDTO(
        @Schema(description = "ID do usuário", example = "1", required = true)
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @Schema(description = "Nome do usuário", example = "John Doe", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        @Schema(description = "E-mail do usuário", example = "johndoe@gmail.com", required = true)
        @NotBlank(message = "Campo obrigatório!")
        @Email(message = "Campo email preenchido incorretamente.")
        String email
) {
}
