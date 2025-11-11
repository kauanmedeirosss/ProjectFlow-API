package io.github.kauanmedeirosss.ProjectFlow_API.repository;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    @Query("SELECT e FROM Equipe e " +
            "LEFT JOIN FETCH e.membros " +
            "LEFT JOIN FETCH e.projetos " +
            "WHERE e.id = :id")
    Optional<Equipe> findByIdWithMembrosAndProjetos(@Param("id") Long id);

}
