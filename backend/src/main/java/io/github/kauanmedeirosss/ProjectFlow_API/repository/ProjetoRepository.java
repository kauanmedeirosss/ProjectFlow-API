package io.github.kauanmedeirosss.ProjectFlow_API.repository;

import io.github.kauanmedeirosss.ProjectFlow_API.model.Projeto;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByEquipeMembrosId(Long usuarioId);
    long countByStatus(StatusProjeto status);
}
