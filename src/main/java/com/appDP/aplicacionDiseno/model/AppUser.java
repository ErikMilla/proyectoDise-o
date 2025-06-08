package com.appDP.aplicacionDiseno.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
@Data
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellidos;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefono;
    private String direccion;
    
    @Column(name = "contacto_emergencia")
    private String contactoEmergencia;

    @Column(nullable = false)
    private String contraseña;

    @Column(name = "fechaCreacion", columnDefinition = "DATETIME")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
