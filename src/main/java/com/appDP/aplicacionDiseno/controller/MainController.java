package com.appDP.aplicacionDiseno.controller;

import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.MedidaCorporal;
import com.appDP.aplicacionDiseno.model.SignosVitales;
import com.appDP.aplicacionDiseno.repository.MedidaCorporalRepository;
import com.appDP.aplicacionDiseno.repository.SignosVitalesRepository;
import com.appDP.aplicacionDiseno.service.interfaces.AppUserService;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List; 

@Controller
public class MainController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MedidaCorporalRepository medidaCorporalRepository;

    @Autowired
    private SignosVitalesRepository signosVitalesRepository;

    @GetMapping({ "", "/" })
public String inicio(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
        AppUser usuario = appUserService.obtenerUsuarioPorEmail(auth.getName());
        model.addAttribute("nombreUsuario", usuario.getNombre());

        // Últimos datos directos
        MedidaCorporal medida = medidaCorporalRepository.findTopByUsuarioOrderByFechaRegistroDesc(usuario);
        if (medida != null) {
            model.addAttribute("peso", medida.getPeso());
            model.addAttribute("estatura", medida.getEstatura());
        }

        SignosVitales signos = signosVitalesRepository.findTopByUsuarioOrderByFechaRegistroDesc(usuario);
        if (signos != null) {
            model.addAttribute("glucosa", signos.getGlucosa());
            model.addAttribute("frecuenciaCardiaca", signos.getFrecuenciaCardiaca());
        }

        // Para gráficos (históricos)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        // Glucosa y Frecuencia
        List<SignosVitales> signosList = signosVitalesRepository.findAllByUsuarioOrderByFechaRegistroAsc(usuario);
        List<String> fechasGlucosa = signosList.stream()
                .map(s -> s.getFechaRegistro().format(formatter)).collect(Collectors.toList());
        List<Double> valoresGlucosa = signosList.stream()
                .map(SignosVitales::getGlucosa).collect(Collectors.toList());
        List<String> fechasFrecuencia = signosList.stream()
                .map(s -> s.getFechaRegistro().format(formatter)).collect(Collectors.toList());
        List<Double> valoresFrecuencia = signosList.stream()
                .map(SignosVitales::getFrecuenciaCardiaca).collect(Collectors.toList());

        // Peso
        List<MedidaCorporal> medidasList = medidaCorporalRepository.findAllByUsuarioOrderByFechaRegistroAsc(usuario);
        List<String> fechasPeso = medidasList.stream()
                .map(m -> m.getFechaRegistro().format(formatter)).collect(Collectors.toList());
        List<Double> valoresPeso = medidasList.stream()
                .map(MedidaCorporal::getPeso).collect(Collectors.toList());

        // Agregar al modelo
        model.addAttribute("fechasGlucosa", fechasGlucosa);
        model.addAttribute("valoresGlucosa", valoresGlucosa);
        model.addAttribute("fechasFrecuencia", fechasFrecuencia);
        model.addAttribute("valoresFrecuencia", valoresFrecuencia);
        model.addAttribute("fechasPeso", fechasPeso);
        model.addAttribute("valoresPeso", valoresPeso);
    }

    return "index";
}
}
