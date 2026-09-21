package com.vetSystem.vet_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioDTO {

    private Long id;
    @NotBlank(message = "El nombre es obligatrio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "La matricula es obligatoria")
    @Pattern(regexp="MV-\\d+", message = "La matricula debe tener el formato MV-XXXX")
    private String matricula;
    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;
}
