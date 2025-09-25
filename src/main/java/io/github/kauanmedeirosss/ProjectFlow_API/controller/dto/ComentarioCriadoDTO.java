package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComentarioCriadoDTO(
        @Schema(description = "Conteúdo do comentário", example = "Bugs na tela de login consertados", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String conteudo,
        @Schema(description = "ID da tarefa a qual o comentário pertence", example = "1", required = true)
        @NotNull(message = "Campo obrigatório!")
        Long tarefa_id,
        @Schema(description = "ID do autor do comentário", example = "1", required = true)
        @NotNull(message = "Campo obrigatório!")
        Long autor_id
) {
}
