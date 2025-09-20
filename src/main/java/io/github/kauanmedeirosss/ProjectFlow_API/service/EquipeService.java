package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.EquipeRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.EquipeMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.EquipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipeService {

    private final EquipeRepository repository;
    private final EquipeMapper mapper;

    public void salvar(EquipeCriadaDTO dto){
        var equipe = mapper.toEntity(dto);
        repository.save(equipe);
    }

    public EquipeRetornoDTO buscarPorId(Long id){
        var equipe = repository.getReferenceById(id);
        return mapper.toRetornoDTO(equipe);
    }

    public List<EquipeRetornoDTO> listarTodas(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

}
