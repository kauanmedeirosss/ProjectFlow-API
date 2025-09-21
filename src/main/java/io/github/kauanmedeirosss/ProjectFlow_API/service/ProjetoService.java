package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoAtualizadoStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.ProjetoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada!"));

        projeto.setEquipe(equipe);

        repository.save(projeto);
    }

    public ProjetoRetornoDTO buscarPorId(Long id){
        var projeto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));
        return mapper.toRetornoDTO(projeto);
    }

    public List<ProjetoRetornoDTO> listarTodas(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

    public void atualizar(ProjetoAtualizadoDTO dto){
        var projeto = repository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));

        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setDeadline(dto.deadline());
        projeto.setOrcamento(dto.orcamento());
        repository.save(projeto);
    }

    public void deletar(Long id){
        var projeto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));
        repository.delete(projeto);
    }

    public void atualizarStatus(Long id, ProjetoAtualizadoStatusDTO dto){
        var projeto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));
        projeto.setStatus(dto.status());
        repository.save(projeto);
    }

}
