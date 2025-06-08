package com.appDP.aplicacionDiseno.service.impl;

import com.appDP.aplicacionDiseno.model.Alerta;
import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.repository.AlertaRepository;
import com.appDP.aplicacionDiseno.service.interfaces.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertaServiceImpl implements AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Override
    public void registrarAlerta(String tipoAlerta, AppUser usuario) {
        Alerta alerta = new Alerta();
        alerta.setFechaHora(LocalDateTime.now());
        alerta.setTipoAlerta(tipoAlerta);
        alerta.setContactoNotificado(usuario.getContactoEmergencia());
        alerta.setUsuario(usuario);
        alertaRepository.save(alerta);
    }
}
