package com.appDP.aplicacionDiseno.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistroDto {
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellidos;
    @NotEmpty @Email
    private String email;
    private String telefono;
    @NotEmpty
    private String direccion;
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contraseña;
    private String confircontraseña;
}
