package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UsuarioCriadoDTO(
        @Schema(description = "Nome do usuário", example = "John Doe", required = true)
        @NotBlank(message = "Campo obrigatório!")
        @Size(max = 100)
        String nome,
        @Schema(description = "E-mail do usuário", example = "johndoe@gmail.com", required = true)
        @NotBlank(message = "Campo obrigatório!")
        @Email(message = "Campo email preenchido incorretamente.")
        @Size(max = 255)
        String email,
        @Schema(description = "Senha do usuário", example = "senha123", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String senha,
        @Schema(description = "Role do usuário", example = "MEMBRO", required = true)
        @NotNull(message = "Campo obrigatório")
        @Enumerated
        Role role
) {
}
