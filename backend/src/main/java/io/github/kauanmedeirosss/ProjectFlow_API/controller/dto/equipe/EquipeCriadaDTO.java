package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.equipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EquipeCriadaDTO(
        @Schema(description = "Nome da equipe", example = "Equipe de Desenvolvimento", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        @Schema(description = "Descrição da equipe", example = "Equipe de desenvolvimento back-end Java", required = false)
        String descricao
) {
}
