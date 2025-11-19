package com.huertohogar.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huertohogar.usuario.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{

    // buscar por el nombre real del campo en la entidad
    List<Usuario> findByNombre(String nombre);

    @Query("SELECT u FROM Usuario u WHERE u.estado = true")
    List<Usuario> findActiveUsers();


    Optional<Usuario>findByCorreo(String correo);

    
    
}
