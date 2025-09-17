package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import java.util.List;

public record EquipeDetalheDTO(
        Long id,
        String nome,
        String descricao,
        List<UsuarioRetornoDTO> membros,
        List<ProjetoRetornoDTO> projetos
) {
}
