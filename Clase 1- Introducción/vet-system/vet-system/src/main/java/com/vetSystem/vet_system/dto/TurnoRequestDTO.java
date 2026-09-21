package com.vetSystem.vet_system.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoRequestDTO {

    @FutureOrPresent(message = "La fecha del turno debe ser hoy o futura")
    @Schema(description = "Fecha en la que se realizará el turno", example = "2026-11-20")
    private LocalDate fecha;
    @Schema(description = "Horario en la que se realizará el turno", example = "15:30:00")
    private LocalTime hora;
    @Schema(description = "Motivo por el cual se realizará el turno", example = "Consulta general")
    private String motivo;
    @NotNull(message = "El ID de la mascota es obligatorio")
    @Positive(message = "El ID de la mascota debe ser positivo")
    @Schema(description = "Identificador único de la mascota", example = "1")
    private Long mascotaId;
    @NotNull(message = "El ID del veterinario es obligatorio")
    @Positive(message = "El ID del veterinario debe ser positivo")
    @Schema(description = "Identificador único del veterinario que atenderá al turno")
    private Long veterinarioId;
}
