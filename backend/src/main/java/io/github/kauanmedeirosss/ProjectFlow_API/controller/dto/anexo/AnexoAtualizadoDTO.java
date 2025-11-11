package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnexoAtualizadoDTO(
        @Schema(description = "ID do anexo", example = "1", required = true)
        @NotNull(message = "Campo obrigatório!")
        Long id,
        @Schema(description = "Nome do arquivo", example = "comandos_docker.txt", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String nomeArquivo,
        @Schema(description = "URL do arquivo", example = "https://storage.com/comandos_docker.txt", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String URLarquivo
) {
}
