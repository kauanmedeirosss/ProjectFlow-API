package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;

import java.time.LocalDate;

public record ProjetoProgressoDTO(
        Long id,
        String nome,
        StatusProjeto status,
        LocalDate deadline
) {
}
