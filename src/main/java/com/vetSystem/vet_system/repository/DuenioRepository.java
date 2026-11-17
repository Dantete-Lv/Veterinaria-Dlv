package com.vetSystem.vet_system.repository;

import com.vetSystem.vet_system.entity.Duenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DuenioRepository extends JpaRepository <Duenio, Long> {

    boolean existsByDni(String dni);

    boolean existsByNombre(String nombre);

    boolean existsByEmail(String email);

    Optional<Duenio> findByEmail(String email);

    Optional<Duenio> findByNombre(String nombre);

    Optional<Duenio> findByNombreAndApellido(String nombre, String apellido);
}

