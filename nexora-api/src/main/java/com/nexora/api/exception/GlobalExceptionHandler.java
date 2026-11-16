
package com.nexora.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento de erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarErrosDeValidacao(
            MethodArgumentNotValidException exception) {

        Map<String, String> erros = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(erro -> {
            erros.put(erro.getField(), erro.getDefaultMessage());
        });

        Map<String, Object> resposta = new HashMap<>();

        resposta.put("status", HttpStatus.BAD_REQUEST.value());
        resposta.put("erro", "Erro de validação");
        resposta.put("mensagens", erros);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }
}
