package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.Rol;
import com.appDP.aplicacionDiseno.model.MedidaCorporal;
import com.appDP.aplicacionDiseno.model.SignosVitales;
import com.appDP.aplicacionDiseno.model.Alerta;
import com.appDP.aplicacionDiseno.repository.UsuarioRepository;
import com.appDP.aplicacionDiseno.repository.MedidaCorporalRepository;
import com.appDP.aplicacionDiseno.repository.SignosVitalesRepository;
import com.appDP.aplicacionDiseno.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class DoctorPanelController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MedidaCorporalRepository medidaCorporalRepository;
    @Autowired
    private SignosVitalesRepository signosVitalesRepository;
    @Autowired
    private AlertaRepository alertaRepository;

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor/pacientes")
    public String verPacientes(Model model) {
        List<AppUser> pacientes = usuarioRepository.findAll()
            .stream()
            .filter(u -> u.getRol() == Rol.PACIENTE)
            .toList();
        model.addAttribute("pacientes", pacientes);
        return "doctor-panel";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor/pacientes/{id}")
    public String verDetallePaciente(@PathVariable int id, Model model) {
        AppUser paciente = usuarioRepository.findById(id).orElse(null);
        if (paciente == null || paciente.getRol() != Rol.PACIENTE) {
            return "redirect:/doctor/pacientes";
        }
        // Últimos datos
        MedidaCorporal ultimaMedida = medidaCorporalRepository.findTopByUsuarioOrderByFechaRegistroDesc(paciente);
        SignosVitales ultimosSignos = signosVitalesRepository.findTopByUsuarioOrderByFechaRegistroDesc(paciente);
        // Historial
        List<MedidaCorporal> historialMedidas = medidaCorporalRepository.findAllByUsuarioOrderByFechaRegistroAsc(paciente);
        List<SignosVitales> historialSignos = signosVitalesRepository.findAllByUsuarioOrderByFechaRegistroAsc(paciente);
        // Alertas
        List<Alerta> alertas = alertaRepository.findByUsuarioOrderByFechaHoraDesc(paciente);
        model.addAttribute("paciente", paciente);
        model.addAttribute("ultimaMedida", ultimaMedida);
        model.addAttribute("ultimosSignos", ultimosSignos);
        model.addAttribute("historialMedidas", historialMedidas);
        model.addAttribute("historialSignos", historialSignos);
        model.addAttribute("alertas", alertas);
        return "detalle-paciente";
    }
} 