package com.appDP.aplicacionDiseno.service.impl;

import com.appDP.aplicacionDiseno.dto.RegistroDto;
import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.repository.UsuarioRepository;
import com.appDP.aplicacionDiseno.service.interfaces.AppUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public AppUser registrarNuevoUsuario(RegistroDto dto) {
        AppUser usuario = new AppUser();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setContraseña(encoder.encode(dto.getContraseña()));
        return repo.save(usuario);
    }

    @Override
    public boolean existeEmail(String email) {
        return repo.findByEmail(email) != null;
    }
    @Override
    public AppUser obtenerUsuarioPorEmail(String email) {
        return repo.findByEmail(email);
    }
}
