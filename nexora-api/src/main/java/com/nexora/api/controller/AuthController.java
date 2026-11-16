
package com.nexora.api.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexora.api.dto.AuthResponse;
import com.nexora.api.dto.CadastroRequest;
import com.nexora.api.dto.LoginRequest;
import com.nexora.api.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse cadastrar(
            @Valid @RequestBody CadastroRequest request) {

        return authService.cadastrar(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}
