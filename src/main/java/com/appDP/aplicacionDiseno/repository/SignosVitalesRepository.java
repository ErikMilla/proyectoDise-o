package com.appDP.aplicacionDiseno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.SignosVitales;

@Repository
public interface SignosVitalesRepository extends JpaRepository<SignosVitales, Long> {
    SignosVitales findTopByUsuarioOrderByFechaRegistroDesc(AppUser usuario);

     List<SignosVitales> findAllByUsuarioOrderByFechaRegistroAsc(AppUser usuario);
}
