package com.huertohogar.usuario.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        System.out.println("=== GENERANDO TOKEN ===");
        System.out.println("JWT Secret: [" + jwtSecret + "]");
        System.out.println("JWT Secret length: " + jwtSecret.length());
        System.out.println("Username: " + username);
        System.out.println("Roles: " + roles);
        
        // Lógica para generar el token JWT con la API moderna
        String token = Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(getSigningKey())
                .compact();
        System.out.println("Token generado: " + token);
        System.out.println("======================\n");
        return token;
    }
}
