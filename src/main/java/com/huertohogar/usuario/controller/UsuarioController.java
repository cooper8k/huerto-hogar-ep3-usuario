package com.huertohogar.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huertohogar.usuario.model.Rol;
import com.huertohogar.usuario.model.Usuario;
import com.huertohogar.usuario.service.RolService;
import com.huertohogar.usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    Usuario usuario = new Usuario();

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;

    @GetMapping("/listar")
    public ResponseEntity <List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario nuevUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevUsuario);
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id,@RequestBody Usuario usuario){
        

            Usuario usuario2 = usuarioService.findByIdUsuario(id);
            if (usuario2==null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                
            }
            usuario2.setNombre(usuario.getNombre());
            usuario2.setApellido(usuario.getApellido());
            usuario2.setCorreo(usuario.getCorreo());
            usuario2.setContrasena(usuario.getContrasena());
            usuario2.setEstado(usuario.isEstado());
            // si el usuario envia el rol a actualizar va a validar si existe
            if (usuario.getRol()!=null) {
                Integer rolid = usuario.getRol().getId_rol();
                if (rolid ==null) {
                    return ResponseEntity.badRequest().build();
                }
                Rol rolGestionado = rolService.buscarIdRol(rolid);
                if (rolGestionado == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                usuario2.setRol(rolGestionado);
            }

            usuarioService.actualizUsuario(usuario2);
            return ResponseEntity.ok(usuario2); 
        
    }
    
    
}
