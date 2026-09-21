package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.dto.TurnoRequestDTO;
import com.vetSystem.vet_system.dto.TurnoResponseDTO;
import com.vetSystem.vet_system.entity.EstadoTurno;
import com.vetSystem.vet_system.service.TurnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> getAllTurnos() {
        return ResponseEntity.ok(
                turnoService.getAllTurnos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> getTurnoById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                turnoService.getTurnoById(id)
        );
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<TurnoResponseDTO>> getAgenda(
            @RequestParam Long veterinarioId,
            @RequestParam LocalDate fecha) {

        return ResponseEntity.ok(
                turnoService.getTurnosByVeterinarioYFecha(
                        veterinarioId,
                        fecha
                )
        );
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDTO> createTurno(
            @Valid @RequestBody TurnoRequestDTO request) {

        TurnoResponseDTO nuevo = turnoService.createTurno(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TurnoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoTurno estado,
            @RequestParam(required = false) String observaciones) {

        return ResponseEntity.ok(
                turnoService.actualizarEstado(
                        id,
                        estado,
                        observaciones
                )
        );
    }
}