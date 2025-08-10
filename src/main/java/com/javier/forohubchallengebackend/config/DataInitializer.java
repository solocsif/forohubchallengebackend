package com.javier.forohubchallengebackend.config;

import com.javier.forohubchallengebackend.entities.Usuario;
import com.javier.forohubchallengebackend.repositorio.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByCorreoElectronico("admin@correo.com").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNombre("Administrador");
                usuario.setCorreoElectronico("admin@correo.com");
                usuario.setContrasena(passwordEncoder.encode("123456"));
                usuario.setPerfiles(List.of("ROLE_USER", "ROLE_ADMIN"));

                usuarioRepository.save(usuario);
                System.out.println("✅ Usuario de prueba creado: admin@correo.com / 123456");
            } else {
                System.out.println("ℹ️ Usuario de prueba ya existe.");
            }
        };
    }
}