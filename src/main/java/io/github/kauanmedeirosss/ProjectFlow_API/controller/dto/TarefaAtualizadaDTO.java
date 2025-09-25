package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TarefaAtualizadaDTO(
        @Schema(description = "ID da tarefa", example = "1", required = true)
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @Schema(description = "Título da tarefa", example = "Implementar model User com JPA", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String titulo,
        @Schema(description = "Descrição da tarefa", example = "Implementar model User com JPA para iniciar fase de codificar controller", required = false)
        String descricao,
        @Schema(description = "Horas estimadas para realizar a tarefa", example = "3", required = true)
        @PositiveOrZero(message = "Valor não pode ser negativo ou zero!")
        Integer horasEstimadas
) {
}
