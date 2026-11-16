package com.nexora.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.nexora.api.dto.CursoRequest;
import com.nexora.api.dto.CursoResponse;
import com.nexora.api.dto.ModuloResponse;
import com.nexora.api.service.CursoService;
import com.nexora.api.service.ModuloService;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final ModuloService moduloService;

    public CursoController(
            CursoService cursoService,
            ModuloService moduloService) {
        this.cursoService = cursoService;
        this.moduloService = moduloService;
    }

    @GetMapping
    public List<CursoResponse> listarTodos() {
        return cursoService.listarTodos();
    }

    @GetMapping("/{id}")
    public CursoResponse buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id);
    }

    @GetMapping("/{cursoId}/modulos")
    public List<ModuloResponse> listarModulos(@PathVariable Long cursoId) {
        return moduloService.listarPorCurso(cursoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoResponse criar(@RequestBody CursoRequest request) {
        return cursoService.salvar(request);
    }

    @PutMapping("/{id}")
    public CursoResponse atualizar(
            @PathVariable Long id,
            @RequestBody CursoRequest request) {
        return cursoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cursoService.excluir(id);
    }
}