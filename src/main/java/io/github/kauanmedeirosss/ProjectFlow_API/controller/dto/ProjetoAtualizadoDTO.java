package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoAtualizadoDTO(
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @NotBlank(message = "Campo obrigatório!")
        String nome,
        String descricao,
        @NotNull(message = "Campo obrigatório")
        @Future(message = "Data não pode ser passada")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate deadline,
        @Positive(message = "Valor não pode ser negativo!")
        BigDecimal orcamento
) {
}
