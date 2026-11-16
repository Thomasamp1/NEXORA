package com.nexora.api.dto;

public record AulaRequest(
    String titulo,
    String descricao,
    String conteudo,
    Integer ordem,
    Integer duracaoMinutos,
    Long moduloId
) {
}