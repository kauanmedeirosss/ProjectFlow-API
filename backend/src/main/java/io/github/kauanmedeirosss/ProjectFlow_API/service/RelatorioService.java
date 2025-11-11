package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoProgressoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.ProjetoMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final ProjetoRepository projetoRepository;
    private final ProjetoMapper projetoMapper;

    public List<ProjetoProgressoDTO> listarProgressosProjetos(){
        var lista = projetoRepository.findAll();
        return lista.stream()
                .map(projetoMapper::toProgressoDTO)
                .toList();
    }

}
