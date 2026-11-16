
package com.nexora.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nexora.api.model.Usuario;
import com.nexora.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Cadastrar usuário
    public Usuario cadastrar(Usuario usuario) {

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "E-mail já cadastrado"
            );
        }

        String senhaCriptografada =
                passwordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    // Listar usuários
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));
    }

    // Atualizar usuário
    public Usuario atualizar(Long id, Usuario novosDados) {

        Usuario usuario = buscarPorId(id);

        if (!usuario.getEmail().equals(novosDados.getEmail())
                && usuarioRepository.findByEmail(novosDados.getEmail()).isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "E-mail já cadastrado"
            );
        }

        usuario.setNome(novosDados.getNome());
        usuario.setEmail(novosDados.getEmail());

        return usuarioRepository.save(usuario);
    }

    // Excluir usuário
    public void excluir(Long id) {

        Usuario usuario = buscarPorId(id);

        usuarioRepository.delete(usuario);
    }
}
