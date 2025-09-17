package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComentarioCriadoDTO(
        @NotBlank(message = "Campo obrigatório!")
        String conteudo,
        @NotNull(message = "Campo obrigatório!")
        Long tarefa_id,
        @NotNull(message = "Campo obrigatório!")
        Long autor_id
) {
}
