package com.vetSystem.vet_system.service;

import com.vetSystem.vet_system.dto.TurnoRequestDTO;
import com.vetSystem.vet_system.dto.TurnoResponseDTO;
import com.vetSystem.vet_system.entity.EstadoTurno;
import com.vetSystem.vet_system.entity.Mascota;
import com.vetSystem.vet_system.entity.Veterinario;
import com.vetSystem.vet_system.exception.ResourceNotFoundException;
import com.vetSystem.vet_system.entity.Turno;
import com.vetSystem.vet_system.exception.StockInsuficienteException;
import com.vetSystem.vet_system.exception.TurnoSuperpuestoException;
import com.vetSystem.vet_system.mappers.MedicamentoMapper;
import com.vetSystem.vet_system.mappers.TurnoMapper;
import com.vetSystem.vet_system.repository.MascotaRepository;
import com.vetSystem.vet_system.repository.TurnoRepository;
import com.vetSystem.vet_system.repository.VeterinarioRepository;
import com.vetSystem.vet_system.dto.MedicamentoResponseDTO;
import com.vetSystem.vet_system.entity.Medicamento;
import com.vetSystem.vet_system.repository.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final TurnoMapper turnoMapper;
    private final MedicamentoRepository medicamentoRepository;
    private final MedicamentoMapper medicamentoMapper;

    public List<TurnoResponseDTO> getAllTurnos(){
        return turnoRepository.findAll().stream().map(turnoMapper::toDTO).collect(Collectors.toList());
    }

    public TurnoResponseDTO getTurnoById(Long id){
        return turnoMapper.toDTO(turnoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Turno", id)));
    }

    public List<TurnoResponseDTO> getTurnosByVeterinarioYFecha(Long veterinarioId, LocalDate fecha){
        return turnoRepository.findByVeterinarioIdAndFecha(veterinarioId, fecha).stream().map(turnoMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public TurnoResponseDTO createTurno(TurnoRequestDTO request) {
        Mascota mascota = mascotaRepository.findById(request.getMascotaId()).orElseThrow(() -> new ResourceNotFoundException("Mascota", request.getMascotaId()));
        Veterinario veterinario = veterinarioRepository.findById(request.getVeterinarioId()).orElseThrow(() -> new ResourceNotFoundException("Veterinario", request.getVeterinarioId()));
        Optional<Turno> turnoConflicto = turnoRepository.findByVeterinarioIdAndFechaAndHora(request.getVeterinarioId(), request.getFecha(), request.getHora());

        if (turnoConflicto.isPresent()) {
            Turno conflicto = turnoConflicto.get();

            throw new TurnoSuperpuestoException("El veterinario ya tiene un turno en ese horario. " + "Turno en conflicto: ID " + conflicto.getId() + ", fecha: " + conflicto.getFecha() + ", hora: " + conflicto.getHora());
        }
        Turno turno = new Turno();
        turno.setFecha(request.getFecha());
        turno.setHora(request.getHora());
        turno.setMotivo(request.getMotivo());
        turno.setEstado(EstadoTurno.PENDIENTE);
        turno.setMascota(mascota);
        turno.setVeterinario(veterinario);
        return turnoMapper.toDTO(turnoRepository.save(turno));
    }

    @Transactional
    public TurnoResponseDTO actualizarEstado(Long id, EstadoTurno nuevoEstado, String observaciones) {
        Turno turno = turnoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Turno", id));
        turno.setEstado(nuevoEstado);
        if (observaciones != null) turno.setObservaciones(observaciones);
        return turnoMapper.toDTO(turnoRepository.save(turno));
    }

    public List<MedicamentoResponseDTO> getMedicamentosByTurno(Long turnoId){

        Turno turno = turnoRepository.findById(turnoId).orElseThrow(() -> new ResourceNotFoundException("Turno", turnoId));

        return turno.getMedicamentos().stream().map(medicamentoMapper::toDTO).collect(Collectors.toList());

    }

    @Transactional
    public MedicamentoResponseDTO agregarMedicamentoATurno(Long turnoId, Long medicamentoId) {

        Turno turno = turnoRepository.findById(turnoId).orElseThrow(() -> new ResourceNotFoundException("Turno", turnoId));

        Medicamento medicamento = medicamentoRepository.findById(medicamentoId).orElseThrow(() -> new ResourceNotFoundException("Medicamento", medicamentoId));

        if (medicamento.getStock() <= 0) {
            throw new StockInsuficienteException("No hay stock disponible para el medicamento: " + medicamento.getNombre());
        }

        turno.getMedicamentos().add(medicamento);

        medicamento.setStock(medicamento.getStock() - 1);

        medicamentoRepository.save(medicamento);
        turnoRepository.save(turno);

        return medicamentoMapper.toDTO(medicamento);
    }

}

