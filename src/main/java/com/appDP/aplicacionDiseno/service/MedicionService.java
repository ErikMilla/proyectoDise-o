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

    // Guardar la información de paciente (Diagnósticos, Medicación, Alergias, Historial Familiar)
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

        return signosVitalesRepository.save(signosVitales);
    }
}
