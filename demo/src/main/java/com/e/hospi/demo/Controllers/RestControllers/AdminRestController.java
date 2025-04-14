package com.e.hospi.demo.Controllers.RestControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e.hospi.demo.Domain.Role;
import com.e.hospi.demo.Domain.Sex;
import com.e.hospi.demo.Dto.UpdateUserDto;
import com.e.hospi.demo.Dto.UserDto;
import com.e.hospi.demo.Dto.UserIdRoleIdSexDto;
import com.e.hospi.demo.Dto.UserResponseDto;
import com.e.hospi.demo.Services.RoleService;
import com.e.hospi.demo.Services.SexService;
import com.e.hospi.demo.Services.UserService;

import jakarta.validation.Valid;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/admin")
@Validated
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;
    private final SexService sexService;

    public AdminRestController (UserService userService, RoleService roleService, SexService sexService) {
        this.userService = userService;
        this.roleService = roleService;
        this.sexService = sexService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> postUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, retornamos los mensajes de error
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }

        try {
            // Crear usuario en el servicio
            userService.createUser(userDto);
            return ResponseEntity.ok().body(Map.of("message", "Usuario creado correctamente"));
        } catch (Exception e) {
            // Manejar cualquier excepción no esperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("message", "Error al crear el usuario: " + e.getMessage()));
        }
    }
        

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        try {
            // Llamada al servicio para obtener todos los roles.
            List<Role> roles = roleService.getAllRoles();
            
            if (roles == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(roles);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los roles: " + e.getMessage());
        }
    }   

    @GetMapping("/sexs")
    public ResponseEntity<?> getAllSexs() {
        try {
            // Llamada al servicio para obtener todos los roles.
            List<Sex> sexs = sexService.getAllSexs();
            
            if (sexs == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(sexs);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los sexos: " + e.getMessage());
        }
    }   

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {

        try {
            // Llamada al servicio para obtener todos los usuarios.
            List<UserResponseDto> users = userService.getAllUsers();
            
            if (users == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(users);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los usuarios: " + e.getMessage());
        }
    }

    @GetMapping("/{emailUser}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String emailUser) {
        try {
        // Llamada al servicio para obtener un usuario por email.
        Optional<UserResponseDto> userResponseDto = userService.getUserByEmail(emailUser);
    
        if (!userResponseDto.isPresent()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    
        return ResponseEntity.ok(userResponseDto.get());
        
        } catch (Exception e) {
                return ResponseEntity.status(500).body("Error al obtener el usuario: " + e.getMessage());
            }
    }

    @PutMapping("/{runUser}")
    public ResponseEntity<?> updateUser(@PathVariable String runUser, @RequestBody @Valid UpdateUserDto updateUserDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }

        try {
            // Llamada al servicio para actualizar el usuario.
            userService.updateUser(runUser, updateUserDto);

            return ResponseEntity.ok("Usuario actualizado correctamente");

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error al actualizar el usuario: " + e.getMessage());
        }
    } 

    
    @GetMapping("/user/{runUser}")
    public ResponseEntity<?> getUserByRun(@PathVariable String runUser) {
        try {
            // Llamada al servicio para obtener un idSex, idRol y email.
            UserIdRoleIdSexDto user = userService.getUserByRun(runUser);
            
            if (user == null) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }

            return ResponseEntity.ok(user);
    
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener el usuario: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{runUser}")
    public ResponseEntity<?> deleteUser(@PathVariable String runUser) {
        try {
            // Llamada al servicio para eliminar el usuario.
            userService.deleteUser(runUser);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar el usuario: " + e.getMessage());
        }

    }
}

