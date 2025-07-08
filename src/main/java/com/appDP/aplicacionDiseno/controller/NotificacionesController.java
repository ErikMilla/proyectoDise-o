package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.ComentarioMedico;
import com.appDP.aplicacionDiseno.model.Alerta;
import com.appDP.aplicacionDiseno.repository.ComentarioMedicoRepository;
import com.appDP.aplicacionDiseno.repository.AlertaRepository;
import com.appDP.aplicacionDiseno.service.interfaces.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class NotificacionesController {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private ComentarioMedicoRepository comentarioMedicoRepository;
    @Autowired
    private AlertaRepository alertaRepository;

    @GetMapping("/notificaciones")
    public String verNotificaciones(Authentication authentication, Model model) {
        AppUser paciente = appUserService.obtenerUsuarioPorEmail(authentication.getName());
        List<ComentarioMedico> comentarios = comentarioMedicoRepository.findByPacienteOrderByFechaComentarioDesc(paciente);
        List<Alerta> alertas = alertaRepository.findByUsuarioOrderByFechaHoraDesc(paciente);
        model.addAttribute("comentarios", comentarios);
        model.addAttribute("alertas", alertas);
        model.addAttribute("activePage", "notificaciones");
        return "notificaciones";
    }
} 