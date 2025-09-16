package io.github.kauanmedeirosss.ProjectFlow_API.model;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Prioridade;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarefas")
public class Tarefa {
    private Long id;
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private StatusTarefa status;
    private Integer horasEstimadas;
    // projeto
    // usuario (cessionario)
}
