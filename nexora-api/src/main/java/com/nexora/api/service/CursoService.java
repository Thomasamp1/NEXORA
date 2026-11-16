package com.nexora.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nexora.api.dto.CursoRequest;
import com.nexora.api.dto.CursoResponse;
import com.nexora.api.model.Curso;
import com.nexora.api.repository.CursoRepository;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<CursoResponse> listarTodos() {
        return cursoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public CursoResponse buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

        return converterParaResponse(curso);
    }

    public CursoResponse salvar(CursoRequest request) {
        Curso curso = new Curso();

        curso.setTitulo(request.titulo());
        curso.setDescricao(request.descricao());
        curso.setNivel(request.nivel());
        curso.setPublicado(request.publicado());

        Curso cursoSalvo = cursoRepository.save(curso);

        return converterParaResponse(cursoSalvo);
    }

    public CursoResponse atualizar(Long id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

        curso.setTitulo(request.titulo());
        curso.setDescricao(request.descricao());
        curso.setNivel(request.nivel());
        curso.setPublicado(request.publicado());

        Curso cursoAtualizado = cursoRepository.save(curso);

        return converterParaResponse(cursoAtualizado);
    }

    public void excluir(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

        cursoRepository.delete(curso);
    }

    private CursoResponse converterParaResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.getNivel(),
                curso.isPublicado(),
                curso.getDataCriacao()
        );
    }
}