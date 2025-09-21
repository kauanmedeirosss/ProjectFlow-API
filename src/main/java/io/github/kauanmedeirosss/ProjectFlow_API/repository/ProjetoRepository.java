package io.github.kauanmedeirosss.ProjectFlow_API.repository;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Projeto;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    // Deleta todas as tarefas de um projeto
    @Modifying
    @Query("DELETE FROM Tarefa t WHERE t.projeto.id = :projetoId")
    void deleteByProjetoId(@Param("projetoId") Long projetoId);
    // Encontra tarefas por projeto
    List<Tarefa> findByProjetoId(Long projetoId);
}
