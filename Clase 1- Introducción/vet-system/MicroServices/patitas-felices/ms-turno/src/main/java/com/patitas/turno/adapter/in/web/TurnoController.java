
package com.patitas.turno.adapter.in.web;

import com.patitas.turno.adapter.in.web.dto.TurnoMapper;
import com.patitas.turno.adapter.in.web.dto.TurnoRequest;
import com.patitas.turno.adapter.in.web.dto.TurnoResponse;
import com.patitas.turno.domain.model.Turno;
import com.patitas.turno.domain.port.in.TurnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
@Tag(name = "Turnos", description = "Operaciones para gestionar turnos veterinarios")
public class TurnoController {

    private final TurnoService turnoService;

    @Operation(summary = "Listar todos los turnos", description = "Obtiene el listado completo de turnos registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<TurnoResponse>> listarTodos() {
        List<TurnoResponse> respuesta = turnoService.listarTodos().stream().map(TurnoMapper::toResponse).toList();
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar turno por ID", description = "Obtiene los datos de un turno utilizando su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Turno encontrado correctamente"), @ApiResponse(responseCode = "404", description = "No se encontró un turno con el ID indicado")})
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponse> buscarPorId(@Parameter(description = "Identificador del turno", example = "1") @PathVariable Long id) {
        Turno turno = turnoService.buscarPorId(id);
        return ResponseEntity.ok(TurnoMapper.toResponse(turno));
    }

    @Operation(summary = "Registrar un turno", description = "Registra un nuevo turno verificando la existencia de la mascota y del veterinario asociados.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Turno registrado correctamente"), @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"), @ApiResponse(responseCode = "404", description = "No se encontró la mascota o el veterinario indicado")})
    @PostMapping
    public ResponseEntity<TurnoResponse> registrar(@Valid @RequestBody TurnoRequest request) {
        Turno creado = turnoService.registrar(TurnoMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(TurnoMapper.toResponse(creado));
    }

    @Operation(summary = "Cancelar un turno", description = "Cambia el estado de un turno existente a CANCELADO.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Turno cancelado correctamente"), @ApiResponse(responseCode = "404", description = "No se encontró un turno con el ID indicado")})
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<TurnoResponse> cancelar(@Parameter(description = "Identificador del turno", example = "1") @PathVariable Long id) {
        Turno cancelado = turnoService.cancelar(id);
        return ResponseEntity.ok(TurnoMapper.toResponse(cancelado));
    }
}
