package com.nexora.api.dto;

import java.time.LocalDateTime;

public record ModuloResponse(
    Long id,
    String titulo,
    String descricao,
    Integer ordem,
    Long cursoId,
    LocalDateTime dataCriacao
) {
}