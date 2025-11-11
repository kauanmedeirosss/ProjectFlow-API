package io.github.kauanmedeirosss.ProjectFlow_API.service;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.PaginaResponseDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoAtualizadoStatusDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.projeto.ProjetoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.exception.ResourceNotFoundException;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.mapper.ProjetoMapper;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Projeto;
import io.github.kauanmedeirosss.ProjectFlow_API.model.enums.StatusProjeto;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.EquipeRepository;
import io.github.kauanmedeirosss.ProjectFlow_API.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository repository;
    private final EquipeRepository equipeRepository;
    private final ProjetoMapper mapper;

    public ProjetoRetornoDTO salvar(ProjetoCriadoDTO dto){
        var projeto = mapper.toEntity(dto);
        projeto.setStatus(StatusProjeto.valueOf("PLANEJAMENTO"));

        var equipe = equipeRepository.findById(dto.equipe_id())
                .orElseThrow(() -> new ResourceNotFoundException("Equipe não encontrada!"));

        projeto.setEquipe(equipe);
        repository.save(projeto);
        return mapper.toRetornoDTO(projeto);
    }

    public ProjetoRetornoDTO buscarPorId(Long id){
        var projeto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));
        return mapper.toRetornoDTO(projeto);
    }

    public PaginaResponseDTO<ProjetoRetornoDTO> listarTodas(Pageable pageable) {
        Page<Projeto> pagina = repository.findAll(pageable);

        List<ProjetoRetornoDTO> conteudo = pagina.getContent()
                .stream()
                .map(mapper::toRetornoDTO)
                .toList();

        return PaginaResponseDTO.of(
                conteudo,
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements(),
                pagina.getTotalPages()
        );
    }

    public ProjetoRetornoDTO atualizar(ProjetoAtualizadoDTO dto){
        var projeto = repository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));

        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setDeadline(dto.deadline());
        projeto.setOrcamento(dto.orcamento());
        repository.save(projeto);
        return mapper.toRetornoDTO(projeto);
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
