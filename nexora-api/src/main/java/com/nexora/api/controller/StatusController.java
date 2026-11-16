
package com.nexora.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/api/status")
    public Map<String, String> verificarStatus() {

        return Map.of(
            "projeto", "NEXORA",
            "status", "Online",
            "mensagem", "Nossa plataforma está começando!"
        );

    }

}