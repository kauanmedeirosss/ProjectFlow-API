package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

public record ComentarioRetornoDTO(
        Long id,
        String conteudo,
        TarefaRetornoDTO tarefa
) {
}
