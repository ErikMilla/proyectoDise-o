package com.appDP.aplicacionDiseno.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mediciones")
public class Medicion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "TEXT")
    private String diagnosticos;
    
    @Column(columnDefinition = "TEXT")
    private String medicacion;
    
    @Column(columnDefinition = "TEXT")
    private String alergias;
    
    @Column(columnDefinition = "TEXT")
    private String historialFamiliar;
    
    private Double peso;
    
    private Double estatura;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
} 