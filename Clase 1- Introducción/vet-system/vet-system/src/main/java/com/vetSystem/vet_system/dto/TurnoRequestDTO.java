package com.vetSystem.vet_system.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoRequestDTO {

    @FutureOrPresent(message = "La feca del turno debe ser hoy o futura")
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    @NotNull(message = "El ID de la mascota es obligatorio")
    @Positive(message = "El ID de la mascota debe ser positivo")
    private Long mascotaId;
    @NotNull(message = "El ID del veterinario es obligatorio")
    @Positive(message = "El ID del veterinario debe ser positivo")
    private Long veterinarioId;
}
