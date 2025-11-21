package com.huertohogar.usuario.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                //ENDPOINTS PUBLICOS
                .requestMatchers(HttpMethod.POST, "/api/v1/usuario/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/usuario/guardar").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/usuario/**").permitAll()

                //ENDPOINTS PROTEGIDOS
                .requestMatchers(HttpMethod.DELETE, "/api/v1/usuario/**").hasRole("Administrador")
                .requestMatchers(HttpMethod.PUT, "/api/v1/usuario/**").hasRole("Administrador")

                .anyRequest().authenticated()
            )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    
}
