package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto;

public record ProjetoMembroDTO(
        Long id,
        String nome,
        String descricao,
        String status,
        String equipeNome
) {}
