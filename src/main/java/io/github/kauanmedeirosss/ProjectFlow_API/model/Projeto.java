package io.github.kauanmedeirosss.ProjectFlow_API.model;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "projetos")
public class Projeto {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate deadline;
    private BigDecimal orcamento;
    private StatusProjeto status;
}
