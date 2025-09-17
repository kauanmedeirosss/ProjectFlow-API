package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.repository.AnexoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnexoService {

    private final AnexoRepository repository;

}
