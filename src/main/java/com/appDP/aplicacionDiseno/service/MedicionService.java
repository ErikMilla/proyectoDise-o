package com.appDP.aplicacionDiseno.service;

import com.appDP.aplicacionDiseno.dto.InfoPacienteDTO;
import com.appDP.aplicacionDiseno.dto.MedidaCorporalDTO;
import com.appDP.aplicacionDiseno.dto.SignosVitalesDTO;
import com.appDP.aplicacionDiseno.model.Alerta;
import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.InfoPaciente;
import com.appDP.aplicacionDiseno.model.MedidaCorporal;
import com.appDP.aplicacionDiseno.model.SignosVitales;
import com.appDP.aplicacionDiseno.repository.AlertaRepository;
import com.appDP.aplicacionDiseno.repository.InfoPacienteRepository;
import com.appDP.aplicacionDiseno.repository.MedidaCorporalRepository;
import com.appDP.aplicacionDiseno.repository.SignosVitalesRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicionService {

    @Autowired
    private InfoPacienteRepository infoPacienteRepository;

    @Autowired
    private MedidaCorporalRepository medidaCorporalRepository;

    @Autowired
    private SignosVitalesRepository signosVitalesRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    // Guardar la información de paciente (Diagnósticos, Medicación, Alergias,
    // Historial Familiar)
    public InfoPaciente guardarInfoPaciente(InfoPacienteDTO infoPacienteDTO, AppUser usuario) {
        InfoPaciente infoPaciente = new InfoPaciente();
        infoPaciente.setDiagnosticos(infoPacienteDTO.getDiagnosticos());
        infoPaciente.setMedicacion(infoPacienteDTO.getMedicacion());
        infoPaciente.setAlergias(infoPacienteDTO.getAlergias());
        infoPaciente.setHistorialFamiliar(infoPacienteDTO.getHistorialFamiliar());
        infoPaciente.setUsuario(usuario);

        return infoPacienteRepository.save(infoPaciente);
    }

    // Guardar las medidas corporales (Peso, Estatura)
    public MedidaCorporal guardarMedidaCorporal(MedidaCorporalDTO medidaCorporalDTO, AppUser usuario) {
        MedidaCorporal medidaCorporal = new MedidaCorporal();
        medidaCorporal.setPeso(medidaCorporalDTO.getPeso());
        medidaCorporal.setEstatura(medidaCorporalDTO.getEstatura());
        medidaCorporal.setUsuario(usuario);

        return medidaCorporalRepository.save(medidaCorporal);
    }

    // Guardar los signos vitales (Glucosa, Frecuencia cardiaca)
    public SignosVitales guardarSignosVitales(SignosVitalesDTO signosVitalesDTO, AppUser usuario) {
        SignosVitales signosVitales = new SignosVitales();
        signosVitales.setGlucosa(signosVitalesDTO.getGlucosa());
        signosVitales.setFrecuenciaCardiaca(signosVitalesDTO.getFrecuenciaCardiaca());
        signosVitales.setUsuario(usuario);

        // Guardar signos vitales en BD
        SignosVitales guardado = signosVitalesRepository.save(signosVitales);

        // Evaluar y registrar alertas si corresponde
        if (guardado.getGlucosa() != null) {
            if (guardado.getGlucosa() < 70) {
                Alerta alertaGlucosaBaja = new Alerta();
                alertaGlucosaBaja.setUsuario(usuario);
                alertaGlucosaBaja.setFechaHora(LocalDateTime.now());
                alertaGlucosaBaja.setTipoAlerta("Glucosa baja");
                alertaGlucosaBaja.setContactoNotificado(usuario.getContactoEmergencia());
                alertaGlucosaBaja.setValorDetectado(guardado.getGlucosa());
                alertaRepository.save(alertaGlucosaBaja);
            } else if (guardado.getGlucosa() > 140) {
                Alerta alertaGlucosaAlta = new Alerta();
                alertaGlucosaAlta.setUsuario(usuario);
                alertaGlucosaAlta.setFechaHora(LocalDateTime.now());
                alertaGlucosaAlta.setTipoAlerta("Glucosa alta");
                alertaGlucosaAlta.setContactoNotificado(usuario.getContactoEmergencia());
                alertaGlucosaAlta.setValorDetectado(guardado.getGlucosa());
                alertaRepository.save(alertaGlucosaAlta);
            }
        }

        // Alerta para Frecuencia Cardíaca
        if (guardado.getFrecuenciaCardiaca() != null) {
            if (guardado.getFrecuenciaCardiaca() < 60) {
                Alerta alertaFrecuenciaBaja = new Alerta();
                alertaFrecuenciaBaja.setUsuario(usuario);
                alertaFrecuenciaBaja.setFechaHora(LocalDateTime.now());
                alertaFrecuenciaBaja.setTipoAlerta("Frecuencia cardíaca baja");
                alertaFrecuenciaBaja.setContactoNotificado(usuario.getContactoEmergencia());
                alertaFrecuenciaBaja.setValorDetectado(guardado.getFrecuenciaCardiaca());
                alertaRepository.save(alertaFrecuenciaBaja);
            } else if (guardado.getFrecuenciaCardiaca() > 100) {
                Alerta alertaFrecuenciaAlta = new Alerta();
                alertaFrecuenciaAlta.setUsuario(usuario);
                alertaFrecuenciaAlta.setFechaHora(LocalDateTime.now());
                alertaFrecuenciaAlta.setTipoAlerta("Frecuencia cardíaca alta");
                alertaFrecuenciaAlta.setContactoNotificado(usuario.getContactoEmergencia());
                alertaFrecuenciaAlta.setValorDetectado(guardado.getFrecuenciaCardiaca());
                alertaRepository.save(alertaFrecuenciaAlta);
            }
        }

        return guardado;
    }

}
