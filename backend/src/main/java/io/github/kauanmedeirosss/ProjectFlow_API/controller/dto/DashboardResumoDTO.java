package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import java.util.Map;

public record DashboardResumoDTO(

        long totalProjetos,
        long totalTarefas,
        long totalUsuarios,
        long totalEquipes,

        Map<String, Long> tarefasPorStatus,
        Map<String, Long> projetosPorStatus

) {}
