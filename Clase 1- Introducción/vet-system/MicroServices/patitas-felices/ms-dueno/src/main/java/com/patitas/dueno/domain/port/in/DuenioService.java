package com.patitas.dueno.domain.port.in;

import com.patitas.dueno.domain.model.Duenio;

import java.util.List;
public interface DuenioService {

    List<Duenio> listarTodos();
    Duenio registrar(Duenio duenio);
    Duenio actualizar(Long id, Duenio duenio);
    void eliminarPorId(Long id);
    Duenio buscarPorId(Long id);
}
