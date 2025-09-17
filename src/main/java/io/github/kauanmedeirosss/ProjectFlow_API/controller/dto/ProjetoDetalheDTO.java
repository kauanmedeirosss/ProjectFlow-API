package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Equipe;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProjetoDetalheDTO(
        Long id,
        String nome,
        String descricao,
        LocalDate dataInicio,
        LocalDate deadline,
        BigDecimal orcamento,
        StatusProjeto status,
        EquipeRetornoDTO equipe,
        List<TarefaRetornoDTO> tarefas
) {
}
