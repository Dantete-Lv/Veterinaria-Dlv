package com.vetSystem.vet_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDTO {

    @NotNull(message = "Este campo debe ser obligatorio")
    @Positive(message = "El Id debe ser positivo")
    @Schema(description = "Identificador único de la mascota", example = "1")
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre de la mascota", example = "Rocco")
    private String nombre;
    @NotBlank(message = "La especie es obligatoria")
    @Schema(description = "Especie de la mascota", example = "Perro")
    private String especie;
    @NotBlank(message = "La raza debe ser obligatoria")
    @Schema(description = "Raza de la mascota", example = "Caniche")
    private String raza;
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    @Schema(description = "Fecha de nacimiento de la mascota", example = "2023-01-20")
    private LocalDate fechaNacimiento;
    @NotNull(message = "Este campo debe ser obligatorio")
    @Positive(message = "El Id debe ser positivo")
    @Schema(description = "Identificador único del dueño de la mascota", example = "1")
    private Long duenioId;
    @NotBlank(message = "Este campo es obligatorio")
    @Schema(description = "Nombre del dueño de la mascota", example = "Dante López Villalba")
    private String duenioNombre;
}
