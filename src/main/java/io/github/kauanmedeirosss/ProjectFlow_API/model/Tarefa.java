package io.github.kauanmedeirosss.ProjectFlow_API.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Prioridade;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tarefas")
@Data
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    @Enumerated(EnumType.STRING)
    private StatusTarefa status;
    @Column(name = "horas_estimadas")
    private Integer horasEstimadas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cessionario_id")
    private Usuario cessionario;
    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<>();
    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Anexo> anexos = new ArrayList<>();
}
