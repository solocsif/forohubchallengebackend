package com.javier.forohubchallengebackend.repositorio;

import com.javier.forohubchallengebackend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre); // 🔑 usado para login

    Optional<Usuario> findByCorreoElectronico(String correoElectronico); // ✅ útil para validaciones
}