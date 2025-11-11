package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.anexo.AnexoRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.AnexoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/anexos")
@RequiredArgsConstructor
@Tag(name = "Anexos", description = "Operações de gerenciamento de anexos")
public class AnexoController {

    private final AnexoService service;

    @Operation(summary = "Criar novo anexo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Anexo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<AnexoRetornoDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do anexo a ser criado",
            required = true)
            @RequestBody @Valid AnexoCriadoDTO dto, UriComponentsBuilder uriBuilder){
        var anexo = service.salvar(dto);
        var uri = uriBuilder.path("/anexos/{id}").buildAndExpand(anexo.id()).toUri();
        return ResponseEntity.created(uri).body(anexo);
    }

    @Operation(summary = "Buscar anexo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anexo encontrado"),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnexoRetornoDTO> buscar(@PathVariable Long id){
        var anexo = service.buscarPorId(id);
        return ResponseEntity.ok(anexo);
    }

    @Operation(
            summary = "Listar todos os anexos",
            description = "Retorna uma lista de todos os anexos cadastrados no sistema"
    )
    @ApiResponse(responseCode = "200", description = "Anexos listados com sucesso")
    @GetMapping
    public ResponseEntity<List<AnexoRetornoDTO>> listar(){
        var anexos = service.listarTodas();
        return ResponseEntity.ok(anexos);
    }

    @Operation(
            summary = "Atualizar um anexo",
            description = "Atualiza um anexo existente passando seu ID no body da requisição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anexo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado"),
    })
    @PutMapping
    @Transactional
    public ResponseEntity<AnexoRetornoDTO> atualizar(@RequestBody @Valid AnexoAtualizadoDTO dto){
        var anexo = service.atualizar(dto);
        return ResponseEntity.ok(anexo);
    }

    @Operation(summary = "Deletar anexo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anexo deletado"),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}