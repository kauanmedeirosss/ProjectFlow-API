package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

}
