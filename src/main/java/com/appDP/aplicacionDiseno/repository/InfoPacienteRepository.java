package com.appDP.aplicacionDiseno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.appDP.aplicacionDiseno.model.InfoPaciente;

@Repository
public interface InfoPacienteRepository extends JpaRepository <InfoPaciente, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, buscar por nombre o apellido
    // List<InfoPaciente> findByNombre(String nombre);
    // List<InfoPaciente> findByApellido(String apellido);

}
