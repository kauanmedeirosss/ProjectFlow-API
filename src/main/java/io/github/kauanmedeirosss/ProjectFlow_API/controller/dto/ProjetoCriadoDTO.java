package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoCriadoDTO(
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        String descricao,
        @NotNull(message = "Campo obrigatório")
        @Future(message = "Data não pode ser passada")
        LocalDate dataInicio,
        @NotNull(message = "Campo obrigatório")
        @Future(message = "Data não pode ser passada")
        LocalDate deadline,
        @Positive(message = "Valor não pode ser negativo!")
        BigDecimal orcamento,
        @NotNull(message = "Campo obrigatório")
        Long equipe_id
) {
}
