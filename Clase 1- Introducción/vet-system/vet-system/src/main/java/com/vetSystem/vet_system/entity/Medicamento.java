package com.vetSystem.vet_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String principioActivo;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private BigDecimal precioUnitario;

}
