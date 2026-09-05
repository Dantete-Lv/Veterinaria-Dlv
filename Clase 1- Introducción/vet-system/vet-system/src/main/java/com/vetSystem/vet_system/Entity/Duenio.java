package com.vetSystem.vet_system.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "duenios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Duenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String dni;

    private String telefono;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "duenio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Mascota> mascotas;
}
