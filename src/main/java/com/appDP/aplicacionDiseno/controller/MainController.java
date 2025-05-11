package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.service.interfaces.AppUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping({"", "/"})
    public String inicio(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            AppUser user = appUserService.obtenerUsuarioPorEmail(auth.getName());
            model.addAttribute("nombreUsuario", user.getNombre()); // "Juan", por ejemplo
        }
        return "index";
    }

    @GetMapping("/medicion")
    public String medicion(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            AppUser user = appUserService.obtenerUsuarioPorEmail(auth.getName());
            model.addAttribute("nombreUsuario", user.getNombre());
        }
        return "medicion";
    }
}
