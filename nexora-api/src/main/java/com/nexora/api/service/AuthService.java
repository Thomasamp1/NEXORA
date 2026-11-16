
package com.nexora.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nexora.api.dto.AuthResponse;
import com.nexora.api.dto.CadastroRequest;
import com.nexora.api.dto.LoginRequest;
import com.nexora.api.model.Usuario;
import com.nexora.api.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse cadastrar(CadastroRequest request) {

        Usuario usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());

        Usuario usuarioSalvo = usuarioService.cadastrar(usuario);

        String token = jwtService.gerarToken(usuarioSalvo.getEmail());

        return new AuthResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                token,
                "Bearer"
        );
    }

    public AuthResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "E-mail ou senha inválidos"
                ));

        boolean senhaCorreta = passwordEncoder.matches(
                request.senha(),
                usuario.getSenha()
        );

        if (!senhaCorreta) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "E-mail ou senha inválidos"
            );
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new AuthResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                token,
                "Bearer"
        );
    }
}
