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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


// listar roles
@RestController
@RequestMapping("api/v1/rol")
@Tag(name = "Roles", description = "controlador para gestionar Roles")
public class RolController {
    @Autowired
    private RolService rolService;

    Rol rol = new Rol();

    @GetMapping("/listar")
    @Operation(summary = "lista los roles", description = "obtiene una lista con todos los roles disponibles")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "lista de los roles obtenidos correctamente",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "204",description = "nose encontraros roles")

    })
    public ResponseEntity<List<Rol>> listarRoles(){
        List<Rol> roles = rolService.listarRoles();
        return ResponseEntity.ok(roles);
    }


    // crear un nuevo rol
    @PostMapping("/guardar")
    @Operation(summary = "Guardar rol nuevo",description = "Crea un rol en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",description = "Rol creado correctamente",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")
    })
    public ResponseEntity<Rol> guardarRol(@RequestBody Rol rol){
        Rol nueRol = rolService.agregaRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueRol);
    }



    
    // actualizar roles por su ID
    @PutMapping("/{id}/actualizar")
    @Operation(summary = "Actualizar un Rol existente por su ID",description = "Actualiza los datos de un Rol por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Rol actiualizado correctamente", content = @Content(mediaType = "application/json",schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "404",description = "Rol no encontrado"),
        @ApiResponse(responseCode = "400",description = "Solicitud inncorrecta"),
        @ApiResponse(responseCode = "500",description = "Error interno de servidor")
    })
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

    // eliminar un rol por su ID
    @DeleteMapping("/{id}/eliminar")
    @Operation(summary = "eliminar un ROl por su ID",description = "Elimina los Roles por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",description = "Rol eliminado correctamente"),
        @ApiResponse(responseCode = "404",description = "Rol no econtrado"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor")

    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try {
            rolService.eliminarRol(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    

}
