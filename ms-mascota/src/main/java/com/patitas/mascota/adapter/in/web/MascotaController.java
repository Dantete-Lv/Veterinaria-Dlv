
package com.patitas.mascota.adapter.in.web;

import com.patitas.mascota.adapter.in.web.dto.MascotaMapper;
import com.patitas.mascota.adapter.in.web.dto.MascotaRequest;
import com.patitas.mascota.adapter.in.web.dto.MascotaResponse;
import com.patitas.mascota.domain.model.Mascota;
import com.patitas.mascota.domain.port.in.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
@Tag(name = "Mascotas", description = "Operaciones para gestionar mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @Operation( summary = "Listar todas las mascotas", description = "Obtiene el listado completo de mascotas registradas.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<MascotaResponse>> listarTodos() {
        List<MascotaResponse> respuesta = mascotaService.listarTodos().stream().map(MascotaMapper::toResponse).toList();
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar mascota por ID", description = "Obtiene los datos de una mascota utilizando su identificador."
    )
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Mascota encontrada correctamente"), @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado")})
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> buscarPorId(@Parameter(description = "Identificador de la mascota", example = "1") @PathVariable Long id) {
        Mascota mascota = mascotaService.buscarPorId(id);
        return ResponseEntity.ok(MascotaMapper.toResponse(mascota));
    }

    @Operation(summary = "Registrar una mascota", description = "Registra una nueva mascota asociándola al identificador de su dueño.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Mascota registrada correctamente"), @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")})
    @PostMapping
    public ResponseEntity<MascotaResponse> registrar(@Valid @RequestBody MascotaRequest request) {
        Mascota creada = mascotaService.registrar(MascotaMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(MascotaMapper.toResponse(creada));
    }

    @Operation(summary = "Actualizar una mascota", description = "Actualiza los datos de una mascota existente utilizando su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Mascota actualizada correctamente"), @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"), @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado")})
    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponse> actualizar(@Parameter(description = "Identificador de la mascota", example = "1") @PathVariable Long id, @Valid @RequestBody MascotaRequest request) {
        Mascota actualizada = mascotaService.actualizar(id, MascotaMapper.toEntity(request));
        return ResponseEntity.ok(MascotaMapper.toResponse(actualizada));
    }

    @Operation(summary = "Eliminar una mascota", description = "Elimina permanentemente una mascota utilizando su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Mascota eliminada correctamente"), @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@Parameter(description = "Identificador de la mascota", example = "1") @PathVariable Long id) {
        mascotaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
