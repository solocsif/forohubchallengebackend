package com.javier.forohubchallengebackend.controller;

import com.javier.forohubchallengebackend.dto.TopicoResponse;
import com.javier.forohubchallengebackend.servicio.TopicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topicos")
public class TopicoRestController {

    private final TopicoService servicio;

    public TopicoRestController(TopicoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<TopicoResponse>> listar(Authentication auth) {
        // Puedes usar auth.getName() si necesitas el usuario
        List<TopicoResponse> topicos = servicio.listar();
        return ResponseEntity.ok(topicos);
    }
}

