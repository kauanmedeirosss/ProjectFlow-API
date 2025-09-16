package io.github.kauanmedeirosss.ProjectFlow_API.model;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Role role;
    @ManyToMany(mappedBy = "membros")
    private Set<Equipe> equipe = new HashSet<>();
}
