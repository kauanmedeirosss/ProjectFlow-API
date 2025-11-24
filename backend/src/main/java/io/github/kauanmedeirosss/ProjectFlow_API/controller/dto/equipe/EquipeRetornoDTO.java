package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.equipe;

public record EquipeRetornoDTO(
        Long id,
        String nome,
        String descricao,
        int quantidadeMembros
) {
}
