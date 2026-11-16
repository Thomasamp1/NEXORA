package com.nexora.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.nexora.api.dto.ModuloRequest;
import com.nexora.api.dto.ModuloResponse;
import com.nexora.api.dto.AulaResponse;
import com.nexora.api.service.ModuloService;
import com.nexora.api.service.AulaService;

@RestController
@RequestMapping("/api/modulos")
public class ModuloController {

    private final ModuloService moduloService;
    private final AulaService aulaService;

    public ModuloController(
            ModuloService moduloService,
            AulaService aulaService) {
        this.moduloService = moduloService;
        this.aulaService = aulaService;
    }

    @GetMapping
    public List<ModuloResponse> listarTodos() {
        return moduloService.listarTodos();
    }

    @GetMapping("/{id}")
    public ModuloResponse buscarPorId(@PathVariable Long id) {
        return moduloService.buscarPorId(id);
    }

    @GetMapping("/{moduloId}/aulas")
    public List<AulaResponse> listarAulas(@PathVariable Long moduloId) {
        return aulaService.listarPorModulo(moduloId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModuloResponse criar(@RequestBody ModuloRequest request) {
        return moduloService.salvar(request);
    }

    @PutMapping("/{id}")
    public ModuloResponse atualizar(
            @PathVariable Long id,
            @RequestBody ModuloRequest request) {
        return moduloService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        moduloService.excluir(id);
    }
}