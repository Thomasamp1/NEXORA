package com.nexora.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nexora.api.dto.ModuloRequest;
import com.nexora.api.dto.ModuloResponse;
import com.nexora.api.model.Curso;
import com.nexora.api.model.Modulo;
import com.nexora.api.repository.CursoRepository;
import com.nexora.api.repository.ModuloRepository;

@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;

    public ModuloService(
            ModuloRepository moduloRepository,
            CursoRepository cursoRepository) {
        this.moduloRepository = moduloRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<ModuloResponse> listarTodos() {
        return moduloRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }
    
public List<ModuloResponse> listarPorCurso(Long cursoId) {
    return moduloRepository.findByCursoIdOrderByOrdemAsc(cursoId)
            .stream()
            .map(this::converterParaResponse)
            .toList();
}
    public ModuloResponse buscarPorId(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo não encontrado."));

        return converterParaResponse(modulo);
    }

    public ModuloResponse salvar(ModuloRequest request) {
        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

        Modulo modulo = new Modulo();

        modulo.setTitulo(request.titulo());
        modulo.setDescricao(request.descricao());
        modulo.setOrdem(request.ordem());
        modulo.setCurso(curso);

        Modulo moduloSalvo = moduloRepository.save(modulo);

        return converterParaResponse(moduloSalvo);
    }

    public ModuloResponse atualizar(Long id, ModuloRequest request) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo não encontrado."));

        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

        modulo.setTitulo(request.titulo());
        modulo.setDescricao(request.descricao());
        modulo.setOrdem(request.ordem());
        modulo.setCurso(curso);

        Modulo moduloAtualizado = moduloRepository.save(modulo);

        return converterParaResponse(moduloAtualizado);
    }

    public void excluir(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo não encontrado."));

        moduloRepository.delete(modulo);
    }

    private ModuloResponse converterParaResponse(Modulo modulo) {
        return new ModuloResponse(
                modulo.getId(),
                modulo.getTitulo(),
                modulo.getDescricao(),
                modulo.getOrdem(),
                modulo.getCurso().getId(),
                modulo.getDataCriacao()
        );
    }
}