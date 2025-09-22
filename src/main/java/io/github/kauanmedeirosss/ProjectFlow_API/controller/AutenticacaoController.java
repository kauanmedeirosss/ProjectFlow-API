package io.github.kauanmedeirosss.ProjectFlow_API.controller;

import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.AutenticacaoDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.controller.dto.RetornaTokenJwtDTO;
import io.github.kauanmedeirosss.ProjectFlow_API.model.Usuario;
import io.github.kauanmedeirosss.ProjectFlow_API.seguranca.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid AutenticacaoDTO dto){
        var token = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        var autenticacao = manager.authenticate(token);
        var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new RetornaTokenJwtDTO(tokenJWT));
    }

}
