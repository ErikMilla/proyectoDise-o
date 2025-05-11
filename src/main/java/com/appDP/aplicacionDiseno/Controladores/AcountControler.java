package com.appDP.aplicacionDiseno.Controladores;

import com.appDP.aplicacionDiseno.Modelo.AppUser;
import com.appDP.aplicacionDiseno.Modelo.RegistroDto;
import com.appDP.aplicacionDiseno.Repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AcountControler {
    @Autowired
    private UsuarioRepositorio repo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String register(Model model){
        RegistroDto registerDto = new RegistroDto();
        model.addAttribute("registroDto", registerDto);
        model.addAttribute("success", false);
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("registroDto") RegistroDto registroDto, Model model) {
        if (!registroDto.getContraseña().equals(registroDto.getConfircontraseña())) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "registro";
        }

        if (repo.findByEmail(registroDto.getEmail()) != null) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }

        AppUser nuevoUsuario = new AppUser();
        nuevoUsuario.setNombre(registroDto.getNombre());
        nuevoUsuario.setApellidos(registroDto.getApellidos());
        nuevoUsuario.setEmail(registroDto.getEmail());
        nuevoUsuario.setTelefono(registroDto.getTelefono());
        nuevoUsuario.setDireccion(registroDto.getDireccion());
        nuevoUsuario.setContraseña(passwordEncoder.encode(registroDto.getContraseña()));
        
        repo.save(nuevoUsuario);
        
        model.addAttribute("success", true);
        return "redirect:/login?registroExitoso";
    }
}
