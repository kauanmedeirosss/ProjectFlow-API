package io.github.kauanmedeirosss.ProjectFlow_API.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Table(name = "equipes")
@Data
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private int quantidadeMembros;
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Projeto> projetos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return id != null && id.equals(equipe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void calculaQtMembros(){
        quantidadeMembros = membros.size();
    }
}
