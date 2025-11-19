package com.huertohogar.usuario.service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Usuario> findByIdUsuario(Integer id){
        return usuarioRepository.findById(id);
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

    public Optional<Usuario> findByCorreo(String correo){
        return usuarioRepository.findByCorreo(correo);
    }
}
