package com.appDP.aplicacionDiseno.service;

import com.appDP.aplicacionDiseno.dto.MedicionDTO;
import com.appDP.aplicacionDiseno.model.Medicion;
import com.appDP.aplicacionDiseno.repository.MedicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicionService {

    @Autowired
    private MedicionRepository medicionRepository;

    public Medicion guardarMedicion(MedicionDTO medicionDTO) {
        Medicion medicion = new Medicion();
        medicion.setDiagnosticos(medicionDTO.getDiagnosticos());
        medicion.setMedicacion(medicionDTO.getMedicacion());
        medicion.setAlergias(medicionDTO.getAlergias());
        medicion.setHistorialFamiliar(medicionDTO.getHistorialFamiliar());
        medicion.setPeso(medicionDTO.getPeso());
        medicion.setEstatura(medicionDTO.getEstatura());
        
        return medicionRepository.save(medicion);
    }
} 