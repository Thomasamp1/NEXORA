
package com.nexora.api.dto;

public record AuthResponse(
    Long id,
    String nome,
    String email,
    String token,
    String tipoToken
) {
}
