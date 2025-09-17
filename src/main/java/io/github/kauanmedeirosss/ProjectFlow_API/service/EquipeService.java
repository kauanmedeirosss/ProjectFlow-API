package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.repository.EquipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipeService {

    private final EquipeRepository repository;

}
