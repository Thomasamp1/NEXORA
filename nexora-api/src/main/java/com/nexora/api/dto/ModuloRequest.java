package com.nexora.api.dto;

public record ModuloRequest(
    String titulo,
    String descricao,
    Integer ordem,
    Long cursoId
) {
}