package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.comentario;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa.TarefaRetornoDTO;

public record ComentarioRetornoDTO(
        Long id,
        String conteudo,
        TarefaRetornoDTO tarefa
) {
}
