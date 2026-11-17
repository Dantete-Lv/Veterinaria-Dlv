package com.vetSystem.vet_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenioDTO {

    @Schema(description = "Identificador único del dueño", example = "1")
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del dueño", example = "Dante")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido del dueño", example = "Lopez Villalba")
    private String apellido;
    @Size(min=7, max=8, message = "El DNI debe tener entre 7 y 8 caracteres")
    @Schema(description = "Documento Nacional de Identidad del dueño", example = "47051552")
    private String dni;
    @Schema(description = "Teléfono de contacto del dueño", example = "2227612688")
    private String telefono;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener el formato válido")
    @Schema(description = "Email de contacto del dueño", example = "Lopezvillalbadante17@email.com")
    private String email;
}