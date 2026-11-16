
package com.nexora.api.service;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs) {

        this.secretKey = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(secret)
        );

        this.expirationMs = expirationMs;
    }

    public String gerarToken(String email) {

        Instant agora = Instant.now();

        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plusMillis(expirationMs)))
                .signWith(secretKey)
                .compact();
    }

    public String extrairEmail(String token) {

        return obterClaims(token).getSubject();
    }

    public boolean validarToken(String token, String email) {

        try {
            String emailDoToken = extrairEmail(token);

            return emailDoToken.equals(email);

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private Claims obterClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
