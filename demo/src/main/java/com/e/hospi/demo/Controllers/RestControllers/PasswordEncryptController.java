/* 
package com.e.hospi.demo.Controllers.RestControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e.hospi.demo.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class PasswordEncryptController {

    @Autowired
    private UserService userService;

    @GetMapping("/encrypt-passwords")
    public String encryptPasswords() {
        userService.encryptPasswords();
        return "Contraseñas encriptadas correctamente.";
    }
}

*/