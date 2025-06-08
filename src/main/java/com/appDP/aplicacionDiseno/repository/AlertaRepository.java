package com.appDP.aplicacionDiseno.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.appDP.aplicacionDiseno.model.Alerta;
import com.appDP.aplicacionDiseno.model.AppUser;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
	List<Alerta> findByUsuario(AppUser usuario);
	List<Alerta> findByUsuarioOrderByFechaHoraDesc(AppUser usuario);
	
}
