package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.equipe.EquipeAtualizadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.equipe.EquipeCriadaDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.equipe.EquipeRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.usuario.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.BusinessRuleException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.EquipeMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.UsuarioMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.EquipeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipeService {

    private final EquipeRepository repository;
    private final EquipeMapper mapper;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public EquipeRetornoDTO salvar(EquipeCriadaDTO dto){
        var equipe = mapper.toEntity(dto);
        repository.save(equipe);
        return mapper.toRetornoDTO(equipe);
    }

    public EquipeRetornoDTO buscarPorId(Long id){
        var equipe = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada!"));
        return mapper.toRetornoDTO(equipe);
    }

    public List<EquipeRetornoDTO> listarTodas(){
        var lista = repository.findAll();
        return lista.stream()
                .map(mapper::toRetornoDTO)
                .toList();
    }

    public EquipeRetornoDTO atualizar(EquipeAtualizadaDTO dto){
        var equipe = repository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada!"));
        equipe.setNome(dto.nome());
        equipe.setDescricao(dto.descricao());
        repository.save(equipe);
        return mapper.toRetornoDTO(equipe);
    }

    public void deletar(Long id){
        var equipe = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada!"));

        equipe.getMembros().clear();
        repository.save(equipe);
        repository.delete(equipe);
    }

    public void adicionarMembro(Long equipeId, Long usuarioId){
        var equipe = repository.findById(equipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada!"));
        var usuario = usuarioService.retornaEntidade(usuarioId);

        if (equipe.getMembros().contains(usuario)) {
            throw new BusinessRuleException("Usuário já é membro desta equipe!");
        }

        // Adicionar usuário à equipe (lado proprietário)
        equipe.getMembros().add(usuario);
        // Adicionar equipe ao usuário (lado inverso - mantém consistência)
        usuario.getEquipe().add(equipe);
        repository.save(equipe);
    }

    public void removerMembro(Long equipeId, Long usuarioId) {
        var equipe = repository.findById(equipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada!"));

        var usuario = usuarioService.retornaEntidade(usuarioId);

        if (!equipe.getMembros().contains(usuario)) {
            throw new BusinessRuleException("Usuário não é membro desta equipe");
        }

        // Remover usuário da equipe (lado proprietário)
        equipe.getMembros().remove(usuario);
        // Remover equipe do usuário (lado inverso - mantém consistência)
        usuario.getEquipe().remove(equipe);
        repository.save(equipe);
    }

    public List<UsuarioRetornoDTO> listarMembros(Long equipeId) {
        var equipe = repository.findById(equipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada"));

        // Forçar carregamento da coleção LAZY
        Hibernate.initialize(equipe.getMembros());

        return equipe.getMembros().stream()
                .map(usuarioMapper::toRetornoDTO)
                .collect(Collectors.toList());
    }

}
