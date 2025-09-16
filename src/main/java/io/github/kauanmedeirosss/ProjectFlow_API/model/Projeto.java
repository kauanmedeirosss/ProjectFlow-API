package io.github.kauanmedeirosss.ProjectFlow_API.model;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projetos")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate deadline;
    private BigDecimal orcamento;
    @Enumerated(EnumType.STRING)
    private StatusProjeto status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id", nullable = false)
    private Equipe equipe;
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tarefa> tarefas = new ArrayList<>();
}
