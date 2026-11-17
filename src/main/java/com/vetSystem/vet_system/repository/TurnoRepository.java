package com.vetSystem.vet_system.repository;

import com.vetSystem.vet_system.entity.Turno;
import com.vetSystem.vet_system.entity.EstadoTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{

    Optional<Turno> findByVeterinarioIdAndFechaAndHora(Long veterinarioId, LocalDate fecha, LocalTime hora);

    List<Turno> findByVeterinarioIdAndFecha(Long veterinarioId, LocalDate fecha);

    List<Turno> findByMascotaIdOrderByFechaDescHoraDesc(Long mascotaId);
}
