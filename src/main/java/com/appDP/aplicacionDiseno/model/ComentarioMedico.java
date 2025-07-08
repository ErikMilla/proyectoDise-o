package com.appDP.aplicacionDiseno.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comentario_medico")
public class ComentarioMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String texto;

    @Column(name = "fecha_comentario")
    private LocalDateTime fechaComentario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private AppUser doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private AppUser paciente;

    @PrePersist
    protected void onCreate() {
        fechaComentario = LocalDateTime.now();
    }
} 