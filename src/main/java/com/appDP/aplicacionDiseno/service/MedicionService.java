package com.appDP.aplicacionDiseno.service;

import com.appDP.aplicacionDiseno.dto.InfoPacienteDTO;
import com.appDP.aplicacionDiseno.dto.MedidaCorporalDTO;
import com.appDP.aplicacionDiseno.dto.SignosVitalesDTO;
import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.InfoPaciente;
import com.appDP.aplicacionDiseno.model.MedidaCorporal;
import com.appDP.aplicacionDiseno.model.SignosVitales;
import com.appDP.aplicacionDiseno.repository.InfoPacienteRepository;
import com.appDP.aplicacionDiseno.repository.MedidaCorporalRepository;
import com.appDP.aplicacionDiseno.repository.SignosVitalesRepository;
import com.appDP.aplicacionDiseno.service.interfaces.AlertaService;
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
    private AlertaService alertaService;

    public InfoPaciente guardarInfoPaciente(InfoPacienteDTO infoPacienteDTO, AppUser usuario) {
        InfoPaciente infoPaciente = new InfoPaciente();
        infoPaciente.setDiagnosticos(infoPacienteDTO.getDiagnosticos());
        infoPaciente.setMedicacion(infoPacienteDTO.getMedicacion());
        infoPaciente.setAlergias(infoPacienteDTO.getAlergias());
        infoPaciente.setHistorialFamiliar(infoPacienteDTO.getHistorialFamiliar());
        infoPaciente.setUsuario(usuario);

        return infoPacienteRepository.save(infoPaciente);
    }

    public MedidaCorporal guardarMedidaCorporal(MedidaCorporalDTO medidaCorporalDTO, AppUser usuario) {
        MedidaCorporal medidaCorporal = new MedidaCorporal();
        medidaCorporal.setPeso(medidaCorporalDTO.getPeso());
        medidaCorporal.setEstatura(medidaCorporalDTO.getEstatura());
        medidaCorporal.setUsuario(usuario);

        return medidaCorporalRepository.save(medidaCorporal);
    }

    public SignosVitales guardarSignosVitales(SignosVitalesDTO signosVitalesDTO, AppUser usuario) {
        SignosVitales signosVitales = new SignosVitales();
        signosVitales.setGlucosa(signosVitalesDTO.getGlucosa());
        signosVitales.setFrecuenciaCardiaca(signosVitalesDTO.getFrecuenciaCardiaca());
        signosVitales.setUsuario(usuario);

        SignosVitales guardado = signosVitalesRepository.save(signosVitales);

        // Lógica de alerta (se usa alertaService para que también se envíe SMS)
        if (guardado.getGlucosa() != null) {
            if (guardado.getGlucosa() < 70) {
                alertaService.registrarAlerta("Glucosa baja", guardado.getGlucosa(), usuario);
            } else if (guardado.getGlucosa() > 140) {
                alertaService.registrarAlerta("Glucosa alta", guardado.getGlucosa(), usuario);
            }
        }

        if (guardado.getFrecuenciaCardiaca() != null) {
            if (guardado.getFrecuenciaCardiaca() < 60) {
                alertaService.registrarAlerta("Frecuencia cardíaca baja", guardado.getFrecuenciaCardiaca(), usuario);
            } else if (guardado.getFrecuenciaCardiaca() > 100) {
                alertaService.registrarAlerta("Frecuencia cardíaca alta", guardado.getFrecuenciaCardiaca(), usuario);
            }
        }

        return guardado;
    }
}
