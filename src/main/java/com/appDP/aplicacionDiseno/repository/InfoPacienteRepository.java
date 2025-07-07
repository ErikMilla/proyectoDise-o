package com.appDP.aplicacionDiseno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.appDP.aplicacionDiseno.model.InfoPaciente;

@Repository
public interface InfoPacienteRepository extends JpaRepository <InfoPaciente, Long> {
}
