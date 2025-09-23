package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AutenticacaoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.RetornaTokenJwtDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Usuario;
import io.github.kauanmedeirosss.ProjectFlow_API.seguranca.TokenService;
import io.github.kauanmedeirosss.ProjectFlow_API.service.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name = "0. Autenticação", description = "Operação de login e autenticação")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AutenticacaoService autenticacaoService;

    @Operation(summary = "Efetuar login")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @PostMapping
    public ResponseEntity<?> efetuarLogin(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciais de login",
            required = true
    )
            @RequestBody @Valid AutenticacaoDTO dto) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
            var authentication = authenticationManager.authenticate(authenticationToken);

            var usuario = (Usuario) authentication.getPrincipal();
            var tokenJWT = tokenService.gerarToken(usuario);

            return ResponseEntity.ok(new RetornaTokenJwtDTO(tokenJWT, usuario.getRole().name()));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

}
