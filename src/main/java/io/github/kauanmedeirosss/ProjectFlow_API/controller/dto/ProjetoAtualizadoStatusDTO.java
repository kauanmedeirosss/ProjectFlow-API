package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record ProjetoAtualizadoStatusDTO(
        @Schema(description = "Novo status do projeto", example = "COMPLETO", required = true)
        @Enumerated
        @NotNull(message = "Campo obrigatório!")
        StatusProjeto status
) {
}
