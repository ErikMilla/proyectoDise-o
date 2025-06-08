package com.appDP.aplicacionDiseno.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alerta")
@Data
@NoArgsConstructor
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    private String tipoAlerta; // "Glucosa alta", "Frecuencia baja", etc.

    private String contactoNotificado;

    @Column(name = "valor_detectado")
    private Double valorDetectado;// Se jala del AppUser

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private AppUser usuario;

}
