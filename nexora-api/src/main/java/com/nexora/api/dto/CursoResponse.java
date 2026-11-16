package com.nexora.api.dto;

import java.time.LocalDateTime;

public record CursoResponse(
    Long id,
    String titulo,
    String descricao,
    String nivel,
    boolean publicado,
    LocalDateTime dataCriacao
) {
}