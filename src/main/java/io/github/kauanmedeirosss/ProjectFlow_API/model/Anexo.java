package io.github.kauanmedeirosss.ProjectFlow_API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "anexos")
public class Anexo {
    private Long id;
    private String nomeArquivo;
    private String URLarquivo;
    private LocalDateTime uploadEm;
    // tarefa
}
