package com.e.hospi.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
        }
        return "login";  // Devolver el nombre de la vista, no el archivo HTML directamente
    }

    // Agregar un método POST para procesar el inicio de sesión
    @PostMapping("/login")
    public String loginSubmit(@RequestParam("email") String email, @RequestParam("password") String password) {
        // Este método puede estar vacío porque Spring Security se encarga del procesamiento real
        return "redirect:/";  // Redirigir a la página principal o a donde se desee
    }

}
