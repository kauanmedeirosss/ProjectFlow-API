package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.PaginaResponseDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.usuario.UsuarioAtualizadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.usuario.UsuarioCriadoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.usuario.UsuarioRetornoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Operações de gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService service;

    @Operation(summary = "Criar novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioRetornoDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário a ser criado",
            required = true)
            @RequestBody @Valid UsuarioCriadoDTO dto, UriComponentsBuilder uriBuilder){
        var usuario = service.salvar(dto);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRetornoDTO> buscar(
            @Parameter(description = "ID do usuário")
            @PathVariable Long id){
       var usuario = service.buscarPorId(id);
       return ResponseEntity.ok(usuario);
    }

    /*
    Exemplo de uso:
    GET /usuarios?pagina=0&tamanho=5&ordenarPor=email
    */
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista paginada de todos os usuários cadastrados no sistema"
    )
    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
    @GetMapping
    public ResponseEntity<PaginaResponseDTO<UsuarioRetornoDTO>> listar(
            @Parameter(description = "Número da página (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int pagina,

            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int tamanho,

            @Parameter(description = "Campo para ordenação", example = "nome")
            @RequestParam(defaultValue = "nome") String ordenarPor)
    {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by(ordenarPor));
        var usuarios = service.listarTodos(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
            summary = "Atualizar um usuário",
            description = "Atualiza um usuário existente passando seu ID no body da requisição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<UsuarioRetornoDTO> atualizar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário a ser atualizado",
            required = true)
            @RequestBody @Valid UsuarioAtualizadoDTO dto){
        var usuario = service.atualizar(dto);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Deletar usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer role ADMIN ou GERENTE")
    })
    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
