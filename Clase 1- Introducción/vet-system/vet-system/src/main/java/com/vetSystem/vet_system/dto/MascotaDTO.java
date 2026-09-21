package com.vetSystem.vet_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDTO {

    @NotNull(message = "Este campo debe ser obligatorio")
    @Positive(message = "El Id debe ser positivo")
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La especie es obligatoria")
    private String especie;
    @NotBlank(message = "La raza debe ser obligatoria")
    private String raza;
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private LocalDate fechaNacimiento;
    @NotNull(message = "Este campo debe ser obligatorio")
    @Positive(message = "El Id debe ser positivo")
    private Long duenioId;
    @NotBlank(message = "Este campo es obligatorio")
    private String duenioNombre;
}
