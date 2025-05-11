package com.appDP.aplicacionDiseno.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainControlador {

    @GetMapping({"", "/"})
    public String inicio() {
        return "index";
    }

    @GetMapping({"/medicion"})
    public String medicion() {
        return "medicion";
    }
}
