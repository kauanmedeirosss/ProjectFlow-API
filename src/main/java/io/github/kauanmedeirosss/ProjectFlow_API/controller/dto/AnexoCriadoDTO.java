package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record AnexoCriadoDTO(
        @NotBlank(message = "Campo obrigatório!")
        String nomeArquivo,
        @NotBlank(message = "Campo obrigatório!")
        String URLarquivo,
        @NotNull(message = "Campo obrigatório!")
        Long tarefa_id
) {
}
