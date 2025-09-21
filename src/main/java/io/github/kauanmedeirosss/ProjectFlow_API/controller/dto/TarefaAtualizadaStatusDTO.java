package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record TarefaAtualizadaStatusDTO(
        @Enumerated
        @NotNull(message = "Campo obrigatório!")
        StatusTarefa status
) {
}
