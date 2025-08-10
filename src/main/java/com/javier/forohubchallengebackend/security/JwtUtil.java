package com.javier.forohubchallengebackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "clave-super-secreta-para-jwt-que-debe-ser-muy-larga";
    private final long EXPIRATION_MS = 86400000; // 1 día

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generarToken(String correoElectronico, List<String> roles) {
        return Jwts.builder()
                .setSubject(correoElectronico)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Este método es el que usa el filtro para obtener el username
    public String obtenerUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Este método es el que usa el filtro para obtener los roles
    public List<String> obtenerRoles(String token) {
        return getClaims(token).get("roles", List.class);
    }

    public boolean validarToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}