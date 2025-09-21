package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipeAtualizadaDTO(
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        String descricao
) {
}
