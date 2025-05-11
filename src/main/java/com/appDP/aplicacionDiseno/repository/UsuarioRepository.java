package com.appDP.aplicacionDiseno.repository;

import com.appDP.aplicacionDiseno.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByEmail(String email);
    
}
