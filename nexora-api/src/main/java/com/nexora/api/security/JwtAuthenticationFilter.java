
package com.nexora.api.security;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nexora.api.repository.UsuarioRepository;
import com.nexora.api.service.JwtService;

import io.jsonwebtoken.JwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UsuarioRepository usuarioRepository) {

        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring(7);

            try {
                System.out.println("========== TESTE JWT ==========");
                System.out.println("Token recebido: " + (token != null));

                String email = jwtService.extrairEmail(token);

                System.out.println("Email extraído: " + email);

                boolean valido = jwtService.validarToken(token, email);

                System.out.println("Token válido: " + valido);

                if (SecurityContextHolder.getContext().getAuthentication() == null
                        && valido) {

                    usuarioRepository.findByEmail(email)
                            .filter(usuario -> usuario.getPerfil() != null)
                            .ifPresent(usuario -> {

                                String autoridade = "ROLE_" +
                                        usuario.getPerfil().name();

                                System.out.println("Perfil encontrado: "
                                        + usuario.getPerfil());

                                UsernamePasswordAuthenticationToken authentication =
                                        new UsernamePasswordAuthenticationToken(
                                                email,
                                                null,
                                                List.of(new SimpleGrantedAuthority(autoridade))
                                        );

                                authentication.setDetails(
                                        new WebAuthenticationDetailsSource()
                                                .buildDetails(request)
                                );

                                SecurityContextHolder.getContext()
                                        .setAuthentication(authentication);
                            });
                }

                System.out.println("Autenticado: "
                        + (SecurityContextHolder.getContext().getAuthentication() != null));

                System.out.println("================================");

            } catch (JwtException | IllegalArgumentException exception) {

                System.out.println("ERRO JWT: " + exception.getMessage());

                SecurityContextHolder.clearContext();
            }
        } else {
            System.out.println("Nenhum Bearer Token recebido.");
        }

        filterChain.doFilter(request, response);
    }
}
