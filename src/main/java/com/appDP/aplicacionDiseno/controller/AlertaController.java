package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.model.Alerta;
import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.repository.AlertaRepository;
import com.appDP.aplicacionDiseno.service.interfaces.AppUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AlertaController {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/alertas")
    public String verAlertas(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            AppUser usuario = appUserService.obtenerUsuarioPorEmail(auth.getName());
            List<Alerta> alertas = alertaRepository.findByUsuarioOrderByFechaHoraDesc(usuario);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            List<Map<String, Object>> alertasFormateadas = alertas.stream().map(alerta -> {
                Map<String, Object> map = new HashMap<>();
                map.put("fechaHora", alerta.getFechaHora().format(formatter));
                map.put("tipoAlerta", alerta.getTipoAlerta());
                map.put("contactoNotificado", alerta.getContactoNotificado());
                map.put("valorDetectado", alerta.getValorDetectado());
                return map;
            }).toList();

            model.addAttribute("alertas", alertasFormateadas);
        }
        return "alertas";
    }

}
