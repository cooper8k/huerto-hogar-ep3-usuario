package com.huertohogar.usuario.controller;

import java.util.List;
import java.util.Optional;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuario")
@Tag(name = "usuarios", description = "controlador para gestionar los Usuarios")
public class UsuarioController {
    Usuario usuario = new Usuario();

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;

    // listar usuarios
    @GetMapping("/listar")
    @Operation(summary = "Lista de usuarios", description = "Obtiene una lista de todos los Usuarios en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista todos los usuarios correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))),
            @ApiResponse(responseCode = "204", description = "no se econtraron usuarios")
    })
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // crear un nuevo usuario
    @PostMapping("/guardar")
    @Operation(summary = "Guarda un usuario nuevo", description = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "crea un usuario correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevUsuario);
    }

    // eliminar un usario por su id
    @DeleteMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar un Usuario por su ID", description = "Elimina un Usuario a travez de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // actualizar un usuario por su id
    @PutMapping("/{id}/actualizar")
    @Operation(summary = "Actualizar Un Usuario", description = "Actualiza los datos de un usuario a travez de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {

        Optional<Usuario> usuarioOpt = usuarioService.findByIdUsuario(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Usuario usuario2 = usuarioOpt.get();
        usuario2.setNombre(usuario.getNombre());
        usuario2.setApellido(usuario.getApellido());
        usuario2.setCorreo(usuario.getCorreo());
        usuario2.setContrasena(usuario.getContrasena());
        usuario2.setEstado(usuario.isEstado());
        // si el usuario envia el rol a actualizar va a validar si existe
        if (usuario.getRol() != null) {
            Integer rolid = usuario.getRol().getId_rol();
            if (rolid == null) {
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
