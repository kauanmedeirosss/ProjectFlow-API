package io.github.kauanmedeirosss.ProjectFlow_API.repository;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByProjetoId(Long projetoId);
}
