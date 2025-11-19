package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Prioridade;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;

public record TarefaRetornoDTO(
        Long id,
        String titulo,
        String descricao,
        Prioridade prioridade,
        Integer horasEstimadas,
        StatusTarefa status,
        String projetoNome,
        String cessionario
) {
}
