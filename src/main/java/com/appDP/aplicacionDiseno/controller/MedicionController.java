package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.dto.MedicionDTO;
import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.service.MedicionService;
import com.appDP.aplicacionDiseno.service.interfaces.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MedicionController {

    @Autowired
    private MedicionService medicionService;
    
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/medicion")
    public String mostrarFormulario(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            AppUser user = appUserService.obtenerUsuarioPorEmail(auth.getName());
            model.addAttribute("nombreUsuario", user.getNombre());
        }
        return "medicion";
    }

    @PostMapping("/guardar-medicion")
    public String guardarMedicion(@ModelAttribute MedicionDTO medicionDTO, 
                                RedirectAttributes redirectAttributes) {
        try {
            medicionService.guardarMedicion(medicionDTO);
            redirectAttributes.addFlashAttribute("mensaje", 
                "Información médica guardada exitosamente");
            return "redirect:/medicion";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al guardar la información médica");
            return "redirect:/medicion";
        }
    }
} 