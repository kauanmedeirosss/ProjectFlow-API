package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;

public record ProjetoRetornoDTO(
        Long id,
        String nome,
        String descricao,
        StatusProjeto status
) {
}
