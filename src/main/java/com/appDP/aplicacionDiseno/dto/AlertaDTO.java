package com.appDP.aplicacionDiseno.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AlertaDTO {
    @NotEmpty
    private LocalDateTime fechaHora;
    @NotEmpty
    private String tipoAlerta; 
    @NotEmpty
    private String contactoNotificado; 
}
