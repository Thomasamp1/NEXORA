
package com.nexora.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexora.api.model.Usuario;
import com.nexora.api.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Cadastrar usuário
    @PostMapping
    public Usuario cadastrar(@Valid @RequestBody Usuario usuario) {
        return usuarioService.cadastrar(usuario);
    }

    // Listar usuários
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public Usuario atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuario) {

        return usuarioService.atualizar(id, usuario);
    }

    // Excluir usuário
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
    }
}
