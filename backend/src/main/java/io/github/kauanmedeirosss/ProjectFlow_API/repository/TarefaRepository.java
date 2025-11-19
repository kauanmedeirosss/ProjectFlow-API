package io.github.kauanmedeirosss.ProjectFlow_API.repository;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("""
        SELECT DISTINCT t
        FROM Tarefa t
        JOIN FETCH t.projeto p
        JOIN FETCH p.equipe e
        LEFT JOIN FETCH t.cessionario c
        WHERE e.id IN :equipeIds
        """)
    List<Tarefa> findAllByEquipeIds(@Param("equipeIds") Collection<Long> equipeIds);
    List<Tarefa> findByProjetoId(Long projetoId);
}
