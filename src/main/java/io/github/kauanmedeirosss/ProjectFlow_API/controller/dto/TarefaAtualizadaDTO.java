package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TarefaAtualizadaDTO(
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @NotBlank(message = "Campo obrigatório!")
        String titulo,
        String descricao,
        @PositiveOrZero(message = "Valor não pode ser negativo ou zero!")
        Integer horasEstimadas
) {
}
