package com.appDP.aplicacionDiseno.repository;

import com.appDP.aplicacionDiseno.model.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
} 