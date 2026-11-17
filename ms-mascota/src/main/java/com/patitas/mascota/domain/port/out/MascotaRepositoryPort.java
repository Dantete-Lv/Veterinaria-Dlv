package com.patitas.mascota.domain.port.out;

import com.patitas.mascota.domain.model.Mascota;

import java.util.List;

public interface MascotaRepositoryPort {
    List<Mascota> buscarTodos();
    Mascota buscarPorId(Long id);
    List<Mascota> buscarPorDuenioId(Long duenioId);
    Mascota guardar(Mascota mascota);
    void eliminarPorId(Long id);
}
