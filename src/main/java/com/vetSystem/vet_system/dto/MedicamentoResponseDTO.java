package com.vetSystem.vet_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoResponseDTO {

    @Schema(description = "Identificador único del medicamento", example = "1")
    private Long id;

    @Schema(description = "Nombre del medicamento", example = "Amoxicilina")
    private String nombre;

    @Schema(description = "Principio activo del medicamento", example = "Amoxicilina trihidrato")
    private String principioActivo;

    @Schema(description = "Cantidad disponible del medicamento", example = "25")
    private Integer stock;

    @Schema(description = "Precio unitario del medicamento", example = "12500.50")
    private BigDecimal precioUnitario;
}