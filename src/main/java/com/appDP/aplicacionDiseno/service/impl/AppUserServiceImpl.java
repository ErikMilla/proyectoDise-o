package com.appDP.aplicacionDiseno.service.impl;

import com.appDP.aplicacionDiseno.dto.RegistroDto;
import com.appDP.aplicacionDiseno.dto.RegistroDoctorDto;
import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.Rol;
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
        usuario.setContactoEmergencia(dto.getContactoEmergencia());
        usuario.setContraseña(encoder.encode(dto.getContraseña()));
        usuario.setRol(Rol.PACIENTE); // Por defecto PACIENTE
        return repo.save(usuario);
    }

    @Override
    public AppUser registrarNuevoDoctor(RegistroDoctorDto dto) {
        AppUser doctor = new AppUser();
        doctor.setNombre(dto.getNombre());
        doctor.setApellidos(dto.getApellidos());
        doctor.setEmail(dto.getEmail());
        doctor.setTelefono(dto.getTelefono());
        doctor.setDireccion(dto.getDireccion());
        doctor.setContactoEmergencia(dto.getContactoEmergencia());
        doctor.setContraseña(encoder.encode(dto.getContraseña()));
        doctor.setRol(Rol.DOCTOR);
        doctor.setNumeroColegiatura(dto.getNumeroColegiatura());
        return repo.save(doctor);
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
