package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoRetornoDTO(
        Long id,
        String nome,
        String descricao,
        LocalDate dataInicio,
        LocalDate deadline,
        BigDecimal orcamento,
        StatusProjeto status,
        String nomeEquipe
) {
}
