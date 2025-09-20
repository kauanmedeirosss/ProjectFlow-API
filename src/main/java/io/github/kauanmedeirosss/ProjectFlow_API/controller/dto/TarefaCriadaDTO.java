package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Prioridade;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record TarefaCriadaDTO(
        @NotBlank(message = "Campo obrigatório!")
        String titulo,
        String descricao,
        @Enumerated
        @NotNull(message = "Campo obrigatório")
        Prioridade prioridade,
        @PositiveOrZero(message = "Valor não pode ser negativo ou zero!")
        Integer horasEstimadas,
        @NotNull(message = "Campo obrigatório")
        Long projeto_id,
        @NotNull(message = "Campo obrigatório")
        Long cessionario
) {
}
