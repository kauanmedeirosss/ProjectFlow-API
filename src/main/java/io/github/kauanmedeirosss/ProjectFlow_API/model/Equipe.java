package io.github.kauanmedeirosss.ProjectFlow_API.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipes")
public class Equipe {
    private Long id;
    private String nome;
    private String descricao;
    @ManyToMany
    @JoinTable(
            name = "membros_equipe",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> membros = new HashSet<>();
}
