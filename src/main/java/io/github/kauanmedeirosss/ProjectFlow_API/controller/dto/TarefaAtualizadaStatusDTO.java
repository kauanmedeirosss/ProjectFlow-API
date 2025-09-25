package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record TarefaAtualizadaStatusDTO(
        @Schema(description = "Novo status da tarefa", example = "FEITA", required = true)
        @Enumerated
        @NotNull(message = "Campo obrigatório!")
        StatusTarefa status
) {
}
