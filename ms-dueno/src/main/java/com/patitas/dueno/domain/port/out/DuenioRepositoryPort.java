package com.patitas.dueno.domain.port.out;

import com.patitas.dueno.domain.model.Duenio;

import java.util.List;
import java.util.Optional;

public interface DuenioRepositoryPort {

    Optional<Duenio> buscarPorNombre(String nombre);
    Optional<Duenio> buscarPorEmail(String email);
    Optional<Duenio> buscarPorNombreYApellido(String nombre, String apellido);
    boolean existePorEmail(String email);
    boolean existePorNombre(String nombre);
    boolean existePorDni(String dni);
    List<Duenio> buscarTodos();
    Duenio guardar(Duenio duenio);
    Duenio buscarPorId(Long id);
    void eliminarPorId(Long id);
}
