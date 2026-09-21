package com.vetSystem.vet_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenioDTO {

    private Long id;
    @NotBlank(message = "El nombre es obligatrio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @Size(min=7, max=8, message = "El DNI debe tener entre 7 y 8 caracteres")
    private String dni;
    private String telefono;
    @Email(message = "El email debe tener el formato válido")
    private String email;
}