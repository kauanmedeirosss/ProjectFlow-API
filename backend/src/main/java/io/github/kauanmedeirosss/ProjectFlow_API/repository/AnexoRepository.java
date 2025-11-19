package io.github.kauanmedeirosss.ProjectFlow_API.repository;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Anexo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnexoRepository extends JpaRepository<Anexo, Long> {
    @Query("SELECT a FROM Anexo a WHERE a.tarefa.id = :tarefaId")
    Page<Anexo> findByTarefaIdPaginado(@Param("tarefaId") Long tarefaId, Pageable pageable);
}
