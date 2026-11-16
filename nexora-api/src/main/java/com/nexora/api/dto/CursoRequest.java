package com.nexora.api.dto;

public record CursoRequest(
    String titulo,
    String descricao,
    String nivel,
    boolean publicado
) {
}