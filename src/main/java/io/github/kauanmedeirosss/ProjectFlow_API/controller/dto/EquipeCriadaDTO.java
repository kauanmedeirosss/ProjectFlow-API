package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record EquipeCriadaDTO(
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        String descricao
) {
}
