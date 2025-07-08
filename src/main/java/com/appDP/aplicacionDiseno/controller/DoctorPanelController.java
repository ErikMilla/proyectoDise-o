package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.Rol;
import com.appDP.aplicacionDiseno.model.MedidaCorporal;
import com.appDP.aplicacionDiseno.model.SignosVitales;
import com.appDP.aplicacionDiseno.model.Alerta;
import com.appDP.aplicacionDiseno.model.ComentarioMedico;
import com.appDP.aplicacionDiseno.dto.ComentarioMedicoDto;
import com.appDP.aplicacionDiseno.repository.UsuarioRepository;
import com.appDP.aplicacionDiseno.repository.MedidaCorporalRepository;
import com.appDP.aplicacionDiseno.repository.SignosVitalesRepository;
import com.appDP.aplicacionDiseno.repository.AlertaRepository;
import com.appDP.aplicacionDiseno.repository.ComentarioMedicoRepository;
import com.appDP.aplicacionDiseno.service.impl.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private ComentarioMedicoRepository comentarioMedicoRepository;
    @Autowired
    private SmsService smsService;

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
        // Comentarios médicos
        List<ComentarioMedico> comentarios = comentarioMedicoRepository.findByPacienteOrderByFechaComentarioDesc(paciente);
        model.addAttribute("paciente", paciente);
        model.addAttribute("ultimaMedida", ultimaMedida);
        model.addAttribute("ultimosSignos", ultimosSignos);
        model.addAttribute("historialMedidas", historialMedidas);
        model.addAttribute("historialSignos", historialSignos);
        model.addAttribute("alertas", alertas);
        model.addAttribute("comentarios", comentarios);
        model.addAttribute("nuevoComentario", new ComentarioMedicoDto());
        return "detalle-paciente";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/doctor/pacientes/{id}/comentario")
    public String agregarComentario(@PathVariable int id, @ModelAttribute("nuevoComentario") ComentarioMedicoDto nuevoComentarioDto,
                                   Authentication authentication, RedirectAttributes redirectAttributes) {
        AppUser paciente = usuarioRepository.findById(id).orElse(null);
        if (paciente == null || paciente.getRol() != Rol.PACIENTE) {
            return "redirect:/doctor/pacientes";
        }
        AppUser doctor = usuarioRepository.findByEmail(authentication.getName());
        ComentarioMedico comentario = new ComentarioMedico();
        comentario.setTexto(nuevoComentarioDto.getTexto());
        comentario.setDoctor(doctor);
        comentario.setPaciente(paciente);
        comentarioMedicoRepository.save(comentario);
        redirectAttributes.addFlashAttribute("mensaje", "Comentario agregado correctamente.");
        return "redirect:/doctor/pacientes/" + id;
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/doctor/pacientes/{id}/sms")
    public String enviarSmsPersonalizado(@PathVariable int id,
                                         @RequestParam("destinatario") String destinatario,
                                         @RequestParam("mensaje") String mensaje,
                                         RedirectAttributes redirectAttributes) {
        AppUser paciente = usuarioRepository.findById(id).orElse(null);
        if (paciente == null || paciente.getRol() != Rol.PACIENTE) {
            return "redirect:/doctor/pacientes";
        }
        String telefono = null;
        if ("paciente".equals(destinatario)) {
            telefono = paciente.getTelefono();
        } else if ("emergencia".equals(destinatario)) {
            telefono = paciente.getContactoEmergencia();
        }
        if (telefono != null && !telefono.isBlank()) {
            if (!telefono.startsWith("+")) {
                telefono = "+51" + telefono;
            }
            smsService.enviarSms(telefono, mensaje);
            redirectAttributes.addFlashAttribute("mensaje", "SMS enviado correctamente a " + telefono);
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo enviar el SMS. Número no válido.");
        }
        return "redirect:/doctor/pacientes/" + id;
    }
} 