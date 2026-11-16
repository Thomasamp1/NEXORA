package com.nexora.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nexora.api.dto.AulaRequest;
import com.nexora.api.dto.AulaResponse;
import com.nexora.api.model.Aula;
import com.nexora.api.model.Modulo;
import com.nexora.api.repository.AulaRepository;
import com.nexora.api.repository.ModuloRepository;

@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final ModuloRepository moduloRepository;

    public AulaService(
            AulaRepository aulaRepository,
            ModuloRepository moduloRepository) {
        this.aulaRepository = aulaRepository;
        this.moduloRepository = moduloRepository;
    }

    public List<AulaResponse> listarTodos() {
        return aulaRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public List<AulaResponse> listarPorModulo(Long moduloId) {
        return aulaRepository.findByModuloIdOrderByOrdemAsc(moduloId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public AulaResponse buscarPorId(Long id) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada."));

        return converterParaResponse(aula);
    }

    public AulaResponse salvar(AulaRequest request) {
        Modulo modulo = moduloRepository.findById(request.moduloId())
                .orElseThrow(() -> new RuntimeException("Módulo não encontrado."));

        Aula aula = new Aula();

        aula.setTitulo(request.titulo());
        aula.setDescricao(request.descricao());
        aula.setConteudo(request.conteudo());
        aula.setOrdem(request.ordem());
        aula.setDuracaoMinutos(request.duracaoMinutos());
        aula.setModulo(modulo);

        Aula aulaSalva = aulaRepository.save(aula);

        return converterParaResponse(aulaSalva);
    }

    public AulaResponse atualizar(Long id, AulaRequest request) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada."));

        Modulo modulo = moduloRepository.findById(request.moduloId())
                .orElseThrow(() -> new RuntimeException("Módulo não encontrado."));

        aula.setTitulo(request.titulo());
        aula.setDescricao(request.descricao());
        aula.setConteudo(request.conteudo());
        aula.setOrdem(request.ordem());
        aula.setDuracaoMinutos(request.duracaoMinutos());
        aula.setModulo(modulo);

        Aula aulaAtualizada = aulaRepository.save(aula);

        return converterParaResponse(aulaAtualizada);
    }

    public void excluir(Long id) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada."));

        aulaRepository.delete(aula);
    }

    private AulaResponse converterParaResponse(Aula aula) {
        return new AulaResponse(
                aula.getId(),
                aula.getTitulo(),
                aula.getDescricao(),
                aula.getConteudo(),
                aula.getOrdem(),
                aula.getDuracaoMinutos(),
                aula.getModulo().getId(),
                aula.getDataCriacao()
        );
    }
}