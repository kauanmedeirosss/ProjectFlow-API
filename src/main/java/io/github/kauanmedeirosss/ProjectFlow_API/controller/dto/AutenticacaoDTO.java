package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(
        @NotBlank(message = "Campo obrigatório!")
        String login,
        @NotBlank(message = "Campo obrigatório!")
        String senha
) {
}
