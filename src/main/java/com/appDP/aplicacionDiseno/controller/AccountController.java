package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.dto.RegistroDto;
import com.appDP.aplicacionDiseno.service.interfaces.AppUserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @Autowired
    private AppUserService userService;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroDto", new RegistroDto());
        return "registro"; // Pasando el DTO vacío para el registro
    }

   @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("registroDto") @Valid RegistroDto dto,
                               BindingResult result, Model model) {

    // Contraseñas no coinciden
    if (!dto.getContraseña().equals(dto.getConfircontraseña())) {
        result.rejectValue("confircontraseña", "error.confircontraseña", "Las contraseñas no coinciden");
    }

    // Correo ya registrado
    if (userService.existeEmail(dto.getEmail())) {
        result.rejectValue("email", "error.email", "El correo electrónico ya está registrado");
    }

    if (result.hasErrors()) {
        return "registro";
    }

    userService.registrarNuevoUsuario(dto);
    model.addAttribute("mensajeExito", "¡Te has registrado con éxito!");
    return "redirect:/login?registroExitoso";
}

}
