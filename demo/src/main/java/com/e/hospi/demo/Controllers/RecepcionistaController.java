package com.e.hospi.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/recepcionista")
public class RecepcionistaController {

    @GetMapping("")
    public String getReceptionistInitialPag() {
        return "receptionist.html";
    }
    
}
