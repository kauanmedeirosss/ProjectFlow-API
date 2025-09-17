package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository repository;

}
