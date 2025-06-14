package com.appDP.aplicacionDiseno.service.interfaces;

import com.appDP.aplicacionDiseno.model.AppUser;

public interface AlertaService {
    void registrarAlerta(String tipoAlerta, Double valorDetectado, AppUser usuario);
}