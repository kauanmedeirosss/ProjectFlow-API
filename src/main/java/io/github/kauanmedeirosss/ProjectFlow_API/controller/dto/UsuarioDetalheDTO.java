package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Role;

import java.util.List;

public record UsuarioDetalheDTO(
        Long id,
        String nome,
        String email,
        String senha,
        Role role,
        List<EquipeRetornoDTO> equipe,
        List<TarefaRetornoDTO> tarefas,
        List<ComentarioRetornoDTO> comentarios
) {
}
