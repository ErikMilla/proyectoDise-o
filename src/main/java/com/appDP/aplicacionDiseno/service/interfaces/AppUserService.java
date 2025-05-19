package com.appDP.aplicacionDiseno.service.interfaces;

import com.appDP.aplicacionDiseno.dto.RegistroDto;
import com.appDP.aplicacionDiseno.model.AppUser;

public interface AppUserService {
    AppUser registrarNuevoUsuario(RegistroDto dto);
    boolean existeEmail(String email);
    AppUser obtenerUsuarioPorEmail(String email);
}
