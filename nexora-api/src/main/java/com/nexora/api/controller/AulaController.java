package com.nexora.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.nexora.api.dto.AulaRequest;
import com.nexora.api.dto.AulaResponse;
import com.nexora.api.service.AulaService;

@RestController
@RequestMapping("/api/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @GetMapping
    public List<AulaResponse> listarTodos() {
        return aulaService.listarTodos();
    }

    @GetMapping("/{id}")
    public AulaResponse buscarPorId(@PathVariable Long id) {
        return aulaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AulaResponse criar(@RequestBody AulaRequest request) {
        return aulaService.salvar(request);
    }

    @PutMapping("/{id}")
    public AulaResponse atualizar(
            @PathVariable Long id,
            @RequestBody AulaRequest request) {
        return aulaService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        aulaService.excluir(id);
    }
}