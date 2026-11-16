package com.nexora.api.dto;

import java.time.LocalDateTime;

public record AulaResponse(
    Long id,
    String titulo,
    String descricao,
    String conteudo,
    Integer ordem,
    Integer duracaoMinutos,
    Long moduloId,
    LocalDateTime dataCriacao
) {
}