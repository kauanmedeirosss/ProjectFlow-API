package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record ProjetoAtualizadoStatusDTO(
        @Enumerated
        @NotNull(message = "Campo obrigatório!")
        StatusProjeto status
) {
}
