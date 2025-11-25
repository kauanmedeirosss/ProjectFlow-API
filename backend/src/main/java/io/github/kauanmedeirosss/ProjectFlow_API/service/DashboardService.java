package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.DashboardResumoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjetoRepository projetoRepository;
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipeRepository equipeRepository;

    public DashboardResumoDTO gerarResumo() {

        long totalProjetos = projetoRepository.count();
        long totalTarefas   = tarefaRepository.count();
        long totalUsuarios  = usuarioRepository.count();
        long totalEquipes   = equipeRepository.count();

        Map<String, Long> tarefasPorStatus = new HashMap<>();
        for (StatusTarefa status : StatusTarefa.values()) {
            tarefasPorStatus.put(status.name(),
                    tarefaRepository.countByStatus(status)
            );
        }

        Map<String, Long> projetosPorStatus = new HashMap<>();
        for (StatusProjeto status : StatusProjeto.values()) {
            projetosPorStatus.put(status.name(),
                    projetoRepository.countByStatus(status)
            );
        }

        return new DashboardResumoDTO(
                totalProjetos,
                totalTarefas,
                totalUsuarios,
                totalEquipes,
                tarefasPorStatus,
                projetosPorStatus
        );
    }
}
