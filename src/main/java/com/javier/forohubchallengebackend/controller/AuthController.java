package com.javier.forohubchallengebackend.controller;

import com.javier.forohubchallengebackend.dto.LoginRequest;
import com.javier.forohubchallengebackend.dto.LoginResponse;
import com.javier.forohubchallengebackend.entities.Usuario;
import com.javier.forohubchallengebackend.repositorio.UsuarioRepository;
import com.javier.forohubchallengebackend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(request.getCorreoElectronico())
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generarToken(usuario.getCorreoElectronico(), usuario.getPerfiles());
        return new LoginResponse(token);
    }
}

