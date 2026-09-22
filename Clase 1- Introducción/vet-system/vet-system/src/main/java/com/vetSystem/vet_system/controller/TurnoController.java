package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.dto.MedicamentoResponseDTO;
import com.vetSystem.vet_system.dto.TurnoRequestDTO;
import com.vetSystem.vet_system.dto.TurnoResponseDTO;
import com.vetSystem.vet_system.entity.EstadoTurno;
import com.vetSystem.vet_system.service.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Turnos", description = "Operaciones relacionadas con los turnos de la clínica veterinaria")
public class TurnoController {

    private final TurnoService turnoService;

    @GetMapping
    @Operation(summary = "Listar turnos", description = "Obtiene la lista completa de turnos registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de turnos obtenida correctamente")
    public ResponseEntity<List<TurnoResponseDTO>> getAllTurnos() {
        return ResponseEntity.ok(turnoService.getAllTurnos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener turno por ID", description = "Busca un turno específico mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Turno encontrado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un turno con el ID indicado")
    public ResponseEntity<TurnoResponseDTO> getTurnoById(@Parameter(description = "ID del turno", example = "1")@PathVariable Long id) {

        return ResponseEntity.ok(turnoService.getTurnoById(id)
        );
    }

    @GetMapping("/{id}/medicamentos")
    @Operation(summary = "Obtener medicamentos de un turno", description = "Obtiene los medicamentos asociados al turno indicado.")
    @ApiResponse(responseCode = "200", description = "Medicamentos obtenidos correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró el turno indicado")
    public ResponseEntity<List<MedicamentoResponseDTO>> getMedicamentosByTurno(@Parameter(description = "ID del turno", example = "1")@PathVariable Long id){
        return ResponseEntity.ok(turnoService.getMedicamentosByTurno(id));
    }

    @PostMapping("/{turnoId}/medicamentos/{medicamentoId}")
    @Operation(summary = "Asociar medicamento a un turno", description = "Asocia un medicamento a un turno y descuenta una unidad de stock")
    @ApiResponse(responseCode = "200", description = "Medicamento asociado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró el turno o medicamento indicado")
    @ApiResponse(responseCode = "422", description = "No hay stock disponible del medicamento")
    public ResponseEntity<MedicamentoResponseDTO> agregarMedicamentoATurno(@Parameter(description = "ID del turno", example = "1")@PathVariable Long turnoId,  @Parameter(description = "ID del medicamento", example = "1") @PathVariable Long medicamentoId){
        return ResponseEntity.ok(turnoService.agregarMedicamentoATurno(turnoId, medicamentoId));
    }

    @GetMapping("/agenda")
    @Operation(summary = "Obtener agenda por ID de veterinario", description = "Busca una agenda específica mediante el identificador de un veterinario.")
    @ApiResponse(responseCode = "200", description = "agenda obtenida correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró información para el veterinario y fecha indicados")
    public ResponseEntity<List<TurnoResponseDTO>> getAgenda(@Parameter(description = "ID del veterinario", example = "1") @RequestParam Long veterinarioId, @Parameter(description = "Fecha de la agenda", example = "2026-09-25") @RequestParam LocalDate fecha) {
        return ResponseEntity.ok(turnoService.getTurnosByVeterinarioYFecha(veterinarioId, fecha));
    }

    @PostMapping
    @Operation(summary = "Crear turno", description = "Registra un nuevo turno en la clínica.")
    @ApiResponse(responseCode = "201", description = "Turno creado correctamente")
    @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
    @ApiResponse(responseCode = "409", description = "Ya existe un turno con los datos indicados")
    public ResponseEntity<TurnoResponseDTO> createTurno(@Valid @RequestBody TurnoRequestDTO request) {

        TurnoResponseDTO nuevo = turnoService.createTurno(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de un turno", description = "Actualiza el estado de un turno existente.")
    @ApiResponse(responseCode = "200", description = "Turno actualizado correctamente")
    @ApiResponse(responseCode = "400",description = "Los datos enviados no son válidos")
    @ApiResponse(responseCode = "404", description = "No se encontró un turno con el ID indicado")
    public ResponseEntity<TurnoResponseDTO> actualizarEstado(@Parameter(description = "ID del turno", example = "1") @PathVariable Long id, @Parameter(description = "Nuevo estado del turno", example = "CONFIRMADO") @RequestParam EstadoTurno estado, @Parameter(description = "Observaciones adicionales del turno", example = "El paciente se encuentra en buen estado") @RequestParam(required = false) String observaciones) {
        return ResponseEntity.ok(turnoService.actualizarEstado(id, estado, observaciones)
        );
    }
}