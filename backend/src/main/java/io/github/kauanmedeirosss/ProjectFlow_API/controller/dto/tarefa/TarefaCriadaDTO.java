package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Prioridade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TarefaCriadaDTO(
        @Schema(description = "Título da tarefa", example = "Implementar model User com JPA", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String titulo,
        @Schema(description = "Descrição da tarefa", example = "Implementar model User com JPA para iniciar fase de codificar controller", required = false)
        String descricao,
        @Schema(description = "Prioridade da tarefa", example = "ALTA", required = true)
        @Enumerated
        @NotNull(message = "Campo obrigatório")
        Prioridade prioridade,
        @Schema(description = "Horas estimadas para realizar a tarefa", example = "3", required = true)
        @PositiveOrZero(message = "Valor não pode ser negativo ou zero!")
        Integer horasEstimadas,
        @Schema(description = "ID do projeto o qual a tarefa pertence", example = "1", required = true)
        @NotNull(message = "Campo obrigatório")
        Long projeto_id,
        @Schema(description = "ID do cessionário da tarefa", example = "1", required = true)
        @NotNull(message = "Campo obrigatório")
        Long cessionario
) {
        public Long getCessionario(){
                return cessionario();
        }
        public Long getProjeto_id(){
                return projeto_id();
        }
}
