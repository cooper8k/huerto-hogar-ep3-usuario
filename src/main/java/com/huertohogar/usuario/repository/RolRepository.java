package com.huertohogar.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huertohogar.usuario.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer>{
    
}
