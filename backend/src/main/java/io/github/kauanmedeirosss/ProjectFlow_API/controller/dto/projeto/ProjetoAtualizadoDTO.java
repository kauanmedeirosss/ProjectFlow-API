package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoAtualizadoDTO(
        @Schema(description = "ID do projeto", example = "1", required = true)
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @Schema(description = "Nome do projeto", example = "Projeção de Fluxos", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        @Schema(description = "Descrição do projeto", example = "Projeção de Fluxos BPMN para os processos internos da instituição", required = false)
        String descricao,
        @Schema(description = "Deadline do projeto", example = "01/01/2026", required = true)
        @NotNull(message = "Campo obrigatório")
        @Future(message = "Data não pode ser passada")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate deadline,
        @Schema(description = "Orçamento do projeto", example = "6000.00", required = true)
        @Positive(message = "Valor não pode ser negativo!")
        BigDecimal orcamento
) {
}
