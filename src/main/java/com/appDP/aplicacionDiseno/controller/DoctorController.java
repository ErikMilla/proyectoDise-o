package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.dto.RegistroDoctorDto;
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
public class DoctorController {

    @Autowired
    private AppUserService userService;

    @GetMapping("/registro-doctor")
    public String mostrarRegistroDoctor(Model model) {
        model.addAttribute("registroDoctorDto", new RegistroDoctorDto());
        return "registro-doctor";
    }

    @PostMapping("/registro-doctor")
    public String procesarRegistroDoctor(@ModelAttribute("registroDoctorDto") @Valid RegistroDoctorDto dto,
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
            return "registro-doctor";
        }

        userService.registrarNuevoDoctor(dto);
        model.addAttribute("mensajeExito", "¡Te has registrado como doctor con éxito!");
        return "redirect:/login?registroExitoso";
    }
} 