package com.appDP.aplicacionDiseno.repository;

import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.model.MedidaCorporal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidaCorporalRepository extends JpaRepository<MedidaCorporal, Long> {
    MedidaCorporal findTopByUsuarioOrderByFechaRegistroDesc(AppUser usuario);
    // Este método busca la medida corporal más reciente de un usuario específico
    List<MedidaCorporal> findAllByUsuarioOrderByFechaRegistroAsc(AppUser usuario);

}