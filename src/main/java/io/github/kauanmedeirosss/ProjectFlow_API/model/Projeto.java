package io.github.kauanmedeirosss.ProjectFlow_API.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projetos")
@Data
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @Column(name = "data_inicio")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    @JsonFormat(pattern = "dd/MM/yyyy")
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
