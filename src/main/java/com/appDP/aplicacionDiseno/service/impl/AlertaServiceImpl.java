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

    @Autowired
    private SmsService smsService;

    @Override
    public void registrarAlerta(String tipoAlerta, Double valorDetectado, AppUser usuario) {
        if (usuario.getContactoEmergencia() == null || usuario.getContactoEmergencia().isBlank())
            return;

        Alerta alerta = new Alerta();
        alerta.setFechaHora(LocalDateTime.now());
        alerta.setTipoAlerta(tipoAlerta);
        alerta.setValorDetectado(valorDetectado);
        alerta.setContactoNotificado(usuario.getContactoEmergencia());
        alerta.setUsuario(usuario);
        alertaRepository.save(alerta);

        // Normalizar número de celular
        String telefono = usuario.getContactoEmergencia();
        if (!telefono.startsWith("+")) {
            telefono = "+51" + telefono;
        }

        String mensaje = "ALERTA: " + tipoAlerta + " detectada. Valor: " + valorDetectado;
        smsService.enviarSms(telefono, mensaje);
        System.out.println("Alerta registrada y SMS enviado a: " + telefono);
    }
}
