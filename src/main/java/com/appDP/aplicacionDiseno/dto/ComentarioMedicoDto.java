package com.appDP.aplicacionDiseno.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ComentarioMedicoDto {
    @NotEmpty(message = "El comentario no puede estar vacío")
    private String texto;
} 