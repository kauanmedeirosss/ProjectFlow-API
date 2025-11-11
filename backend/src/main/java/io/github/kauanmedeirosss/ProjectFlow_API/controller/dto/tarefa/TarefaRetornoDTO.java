package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.tarefa;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.usuario.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;

public record TarefaRetornoDTO(
        Long id,
        String titulo,
        StatusTarefa status,
        UsuarioRetornoDTO cessionario
) {
}
