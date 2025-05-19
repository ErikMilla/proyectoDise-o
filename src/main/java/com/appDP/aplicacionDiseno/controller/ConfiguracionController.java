package com.appDP.aplicacionDiseno.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfiguracionController {

    @GetMapping("/ajustes")
    public String mostrarConfiguracion(Model model) {

        return "ajustes";
    }
}
