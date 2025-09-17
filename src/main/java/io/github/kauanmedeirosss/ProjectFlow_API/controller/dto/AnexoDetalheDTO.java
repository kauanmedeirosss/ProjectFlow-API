package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import java.time.LocalDateTime;

public record AnexoDetalheDTO(
        Long id,
        String nomeArquivo,
        String URLarquivo,
        LocalDateTime uploadEm,
        TarefaRetornoDTO tarefa
) {
}
