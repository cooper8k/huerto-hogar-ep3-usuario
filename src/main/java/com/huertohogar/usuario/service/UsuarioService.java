package com.huertohogar.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huertohogar.usuario.model.Usuario;
import com.huertohogar.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // listar usuario
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    // buscar por id
    public Usuario findByIdUsuario(Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }

    // agregar usuario
    public Usuario saveUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    // eliminar usuario
    public void eliminarUsuario(Integer id){
        usuarioRepository.deleteById(id);
    }

    // actualizar usuario
    public Usuario actualizUsuario(Usuario usuario){
        return usuarioRepository.save(usuario); 
       }

}
