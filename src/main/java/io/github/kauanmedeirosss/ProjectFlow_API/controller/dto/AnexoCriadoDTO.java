package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record AnexoCriadoDTO(
        @Schema(description = "Nome do arquivo", example = "comandos_docker.txt", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String nomeArquivo,
        @Schema(description = "URL do arquivo", example = "https://storage.com/comandos_docker.txt", required = true)
        @NotBlank(message = "Campo obrigatório!")
        String URLarquivo,
        @Schema(description = "ID da tarefa a qual o arquivo pertence", example = "1", required = true)
        @NotNull(message = "Campo obrigatório!")
        Long tarefa_id
) {
        public Long getTarefaId(){
                return tarefa_id();
        }
}
