package com.appDP.aplicacionDiseno.repository;

import com.appDP.aplicacionDiseno.model.ComentarioMedico;
import com.appDP.aplicacionDiseno.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioMedicoRepository extends JpaRepository<ComentarioMedico, Long> {
    List<ComentarioMedico> findByPacienteOrderByFechaComentarioDesc(AppUser paciente);
} 