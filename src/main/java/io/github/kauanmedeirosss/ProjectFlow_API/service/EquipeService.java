package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeDetalheDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Equipe;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.EquipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipeService {

    private final EquipeRepository repository;

    public EquipeDetalheDTO findEquipeDetalhe(Long id) {
        Equipe equipe = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada"));

        return mapToDTO(equipe);
    }

}
