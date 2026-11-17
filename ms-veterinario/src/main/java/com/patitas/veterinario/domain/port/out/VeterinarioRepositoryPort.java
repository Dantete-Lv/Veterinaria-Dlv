package com.patitas.veterinario.domain.port.out;

import com.patitas.veterinario.domain.model.Veterinario;

import java.util.List;

public interface VeterinarioRepositoryPort {

    List<Veterinario> buscarTodos();

    Veterinario buscarPorId(Long id);

    List<Veterinario> buscarPorEspecialidad(String especialidad);

    boolean existePorMatricula(String matricula);

    boolean existePorEmail(String email);

    Veterinario guardar(Veterinario veterinario);

    void eliminarPorId(Long id);
}