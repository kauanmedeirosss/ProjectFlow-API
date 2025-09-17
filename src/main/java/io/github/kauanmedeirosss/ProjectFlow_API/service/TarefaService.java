package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;

}
