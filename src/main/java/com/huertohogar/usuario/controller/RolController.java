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
import com.huertohogar.usuario.service.RolService;

@RestController
@RequestMapping("api/v1/rol")
public class RolController {
    @Autowired
    private RolService rolService;

    Rol rol = new Rol();

    @GetMapping("/listar")
    public ResponseEntity<List<Rol>> listarRoles(){
        List<Rol> roles = rolService.listarRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Rol> guardarRol(@RequestBody Rol rol){
        Rol nueRol = rolService.agregaRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueRol);
    }
    
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Rol> actualizar(@PathVariable Integer id,@RequestBody Rol rol){
        try {
            Rol rol2 = rolService.buscarIdRol(id);
            rol2.setNombre_rol(rol.getNombre_rol());

            rolService.agregaRol(rol2);
            return ResponseEntity.ok(rol2);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try {
            rolService.eliminarRol(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    

}
