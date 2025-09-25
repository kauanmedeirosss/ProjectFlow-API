package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipeAtualizadaDTO(
        @Schema(description = "ID da equipe", example = "1", required = true)
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @Schema(description = "Nome da equipe", example = "Equipe de Desenvolvimento", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        @Schema(description = "Descrição da equipe", example = "Equipe de desenvolvimento back-end Java", required = false)
        String descricao
) {
}
