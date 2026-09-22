package com.vetSystem.vet_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del medicamento", example = "Amoxicilina")
    private String nombre;

    @NotBlank(message = "El principio activo es obligatorio")
    @Schema(description = "Principio activo del medicamento", example = "Amoxicilina trihidrato")
    private String principioActivo;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad disponible del medicamento", example = "25")
    private Integer stock;

    @NotNull(message = "El precio unitario es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Schema(description = "Precio unitario del medicamento", example = "12500.50")
    private BigDecimal precioUnitario;
}