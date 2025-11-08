package com.huertohogar.usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.huertohogar.usuario.model.Rol;
import com.huertohogar.usuario.repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {
    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // listar roles
    public List<Rol> listarRoles(){
        return rolRepository.findAll();
    }

    // buscar por id
    public Rol buscarIdRol(Integer id){
        return rolRepository.findById(id).orElse(null);
    }

    // agregar un rol
    public Rol agregaRol(Rol rol){
        return rolRepository.save(rol);
    }

    // eliminar rol
    public void eliminarRol(Integer id){
        rolRepository.deleteById(id);
    }
}
