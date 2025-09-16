package io.github.kauanmedeirosss.ProjectFlow_API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {
    private Long id;
    private String conteudo;
    private LocalDateTime criadoEm;
    // usuario (autor)
    // tarefa
}
