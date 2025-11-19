package io.github.kauanmedeirosss.ProjectFlow_API.repository;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    @Query("SELECT a FROM Comentario a WHERE a.tarefa.id = :tarefaId")
    Page<Comentario> findByTarefaIdPaginado(@Param("tarefaId") Long tarefaId, Pageable pageable);
}
