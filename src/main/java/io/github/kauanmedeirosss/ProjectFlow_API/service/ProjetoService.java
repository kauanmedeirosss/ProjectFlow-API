package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.ProjetoMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.EquipeRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository repository;
    private final EquipeRepository equipeRepository;
    private final ProjetoMapper mapper;

    public void salvar(ProjetoCriadoDTO dto){
        var projeto = mapper.toEntity(dto);
        projeto.setStatus(StatusProjeto.valueOf("PLANEJAMENTO"));

        var equipe = equipeRepository.findById(dto.equipe_id())
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));

        projeto.setEquipe(equipe);

        repository.save(projeto);
    }

    public ProjetoRetornoDTO buscarPorId(Long id){
        var projeto = repository.getReferenceById(id);
        return mapper.toRetornoDTO(projeto);
    }

    public List<ProjetoRetornoDTO> listarTodas(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

}
