package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(
        @Schema(description = "E-mail do usuário", example = "johndoe@gmail.com", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String login,
        @Schema(description = "Senha do usuário", example = "senha123", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String senha
) {
}
