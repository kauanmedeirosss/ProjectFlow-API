package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;

public record ComentarioRetornoDTO(
        Long id,
        String conteudo,
        Tarefa tarefa
) {
}
