package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoCriadoDTO(
        @Schema(description = "Nome do projeto", example = "Projeção de Fluxos", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        @Schema(description = "Descrição do projeto", example = "Projeção de Fluxos BPMN para os processos internos da instituição", required = false)
        String descricao,
        @Schema(description = "Data de início do projeto", example = "01/01/2025", required = true)
        @NotNull(message = "Campo obrigatório")
        @FutureOrPresent(message = "Data não pode ser passada")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataInicio,
        @Schema(description = "Deadline do projeto", example = "01/01/2026", required = true)
        @NotNull(message = "Campo obrigatório")
        @Future(message = "Data não pode ser passada")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate deadline,
        @Schema(description = "Orçamento do projeto", example = "6000.00", required = true)
        @Positive(message = "Valor não pode ser negativo!")
        BigDecimal orcamento,
        @Schema(description = "ID da equipe a qual o projeto pertence", example = "1", required = true)
        @NotNull(message = "Campo obrigatório")
        Long equipe_id
) {
}
