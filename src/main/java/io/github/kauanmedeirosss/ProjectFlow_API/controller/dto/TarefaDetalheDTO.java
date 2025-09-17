package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Prioridade;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;

import java.util.List;

public record TarefaDetalheDTO(
        Long id,
        String titulo,
        String descricao,
        Prioridade prioridade,
        StatusTarefa status,
        Integer horasEstimadas,
        ProjetoRetornoDTO projeto,
        UsuarioRetornoDTO cessionario,
        List<ComentarioRetornoDTO> comentarios,
        List<AnexoRetornoDTO> anexos
) {
}
