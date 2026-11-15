package com.patitas.mascota.adapter.out.persistence;

import com.patitas.mascota.domain.model.Mascota;
import com.patitas.mascota.domain.port.out.MascotaRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long>, MascotaRepositoryPort {

    List<Mascota> findByDuenioId(Long duenioId);

    @Override
    default List<Mascota> buscarTodos() {
        return findAll();
    }

    @Override
    default Mascota buscarPorId(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    default List<Mascota> buscarPorDuenioId(Long duenioId) {
        return findByDuenioId(duenioId);
    }

    @Override
    default Mascota guardar(Mascota mascota) {
        return save(mascota);
    }

    @Override
    default void eliminarPorId(Long id) {
        deleteById(id);
    }
}
