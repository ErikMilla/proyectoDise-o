package com.appDP.aplicacionDiseno.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "logout", required = false) String logout,
            Model model) {
        
        if (error != null) {
            model.addAttribute("error", true);
        }
        if (logout != null) {
            model.addAttribute("logout", true);
        }

        return "login";
    }
}
