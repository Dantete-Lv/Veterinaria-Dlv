package com.vetSystem.vet_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioDTO {

    @Schema(description = "Identificdor único del veterinario", example = "1")
    private Long id;
    @Schema(description = "Nombre del veterinario", example = "Jorge")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido del veterinario", example = "Pereyra")
    private String apellido;
    @NotBlank(message = "La matricula es obligatoria")
    @Pattern(regexp="MV-\\d+", message = "La matricula debe tener el formato MV-XXXX")
    @Schema(description = "Matrícula del médico", example = "MV-1234")
    private String matricula;
    @NotBlank(message = "La especialidad es obligatoria")
    @Schema(description = "Especialidad del veterinario", example = "Cardiología veterinaria")
    private String especialidad;
}
