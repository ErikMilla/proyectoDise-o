package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.dto.InfoPacienteDTO;
import com.appDP.aplicacionDiseno.dto.MedidaCorporalDTO;
import com.appDP.aplicacionDiseno.dto.SignosVitalesDTO;
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

    @PostMapping("/guardar-info-paciente")
    public String guardarInfoPaciente(@ModelAttribute InfoPacienteDTO infoPacienteDTO, 
                                      RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
                AppUser usuario = appUserService.obtenerUsuarioPorEmail(auth.getName());
                medicionService.guardarInfoPaciente(infoPacienteDTO, usuario);
                redirectAttributes.addFlashAttribute("mensaje", "Información de paciente guardada.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la información.");
        }
        return "redirect:/medicion";
    }

    @PostMapping("/guardar-medida-corporal")
    public String guardarMedidaCorpotal(@ModelAttribute MedidaCorporalDTO medidaCorpotalDTO, 
                                        RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
                AppUser usuario = appUserService.obtenerUsuarioPorEmail(auth.getName());
                medicionService.guardarMedidaCorporal(medidaCorpotalDTO, usuario);
                redirectAttributes.addFlashAttribute("mensaje", "Medidas corporales guardadas.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar las medidas.");
        }
        return "redirect:/medicion";
    }

    @PostMapping("/guardar-signos-vitales")
    public String guardarSignosVitales(@ModelAttribute SignosVitalesDTO signosVitalesDTO, 
                                       RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
                AppUser usuario = appUserService.obtenerUsuarioPorEmail(auth.getName());
                medicionService.guardarSignosVitales(signosVitalesDTO, usuario);
                redirectAttributes.addFlashAttribute("mensaje", "Signos vitales guardados.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar los signos vitales.");
        }
        return "redirect:/medicion";
    }
}
