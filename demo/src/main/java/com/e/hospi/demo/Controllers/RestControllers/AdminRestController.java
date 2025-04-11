package com.e.hospi.demo.Controllers.RestControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e.hospi.demo.Domain.Role;
import com.e.hospi.demo.Domain.Sex;
import com.e.hospi.demo.Dto.UserDto;
import com.e.hospi.demo.Services.RoleService;
import com.e.hospi.demo.Services.SexService;
import com.e.hospi.demo.Services.UserService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/admin")
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
    public ResponseEntity<?> postUser(@Valid @RequestBody UserDto userDto) {
        // Llamada al servicio para crear usuario.
        userService.createUser(userDto);
        return ResponseEntity.ok("Usuario creado correctamente");
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


}
