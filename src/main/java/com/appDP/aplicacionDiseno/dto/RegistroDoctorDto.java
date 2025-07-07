package com.appDP.aplicacionDiseno.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistroDoctorDto {
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotEmpty(message = "Los apellidos son obligatorios")
    private String apellidos;
    
    @NotEmpty(message = "El email es obligatorio") 
    @Email(message = "El formato del email no es válido")
    private String email;
    
    private String telefono;
    
    @NotEmpty(message = "La dirección es obligatoria")
    private String direccion;
    
    @NotEmpty(message = "El contacto de emergencia es obligatorio")
    private String contactoEmergencia;
    
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contraseña;
    
    private String confircontraseña;
    
    @NotEmpty(message = "El número de colegiatura es obligatorio")
    @Pattern(regexp = "^[0-9]{4,10}$", message = "El número de colegiatura debe tener entre 4 y 10 dígitos")
    private String numeroColegiatura;
} 