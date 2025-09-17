package io.github.kauanmedeirosss.ProjectFlow_API.model;

import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(mappedBy = "membros")
    private Set<Equipe> equipe = new HashSet<>();
    @OneToMany(mappedBy = "cessionario", fetch = FetchType.LAZY)
    private List<Tarefa> tarefas = new ArrayList<>();
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<>();
}
