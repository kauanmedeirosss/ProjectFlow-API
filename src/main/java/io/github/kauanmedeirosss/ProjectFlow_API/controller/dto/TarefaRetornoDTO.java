package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;

import java.util.List;

public record TarefaRetornoDTO(
        Long id,
        String titulo,
        StatusTarefa status,
        UsuarioRetornoDTO cessionario
) {
}
