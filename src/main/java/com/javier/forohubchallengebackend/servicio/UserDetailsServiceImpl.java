package com.javier.forohubchallengebackend.servicio;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.javier.forohubchallengebackend.repositorio.UsuarioRepository;
import com.javier.forohubchallengebackend.entities.Usuario;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Correo no registrado"));

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreoElectronico(), // se usa como username
                usuario.getContrasena(),
                usuario.getPerfiles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
