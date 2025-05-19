package com.appDP.aplicacionDiseno.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medidas_corporales")
public class MedidaCorporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double peso;
    private Double estatura;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private AppUser usuario;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}

