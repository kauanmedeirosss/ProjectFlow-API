package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;;

import java.time.LocalDateTime;

public record ComentarioDetalheDTO(
        Long id,
        String conteudo,
        LocalDateTime criadoEm,
        TarefaRetornoDTO tarefa,
        UsuarioRetornoDTO autor
) {
}
