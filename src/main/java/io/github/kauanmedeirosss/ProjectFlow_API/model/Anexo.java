package io.github.kauanmedeirosss.ProjectFlow_API.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "anexos")
public class Anexo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeArquivo;
    private String URLarquivo;
    private LocalDateTime uploadEm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Tarefa tarefa;
}
