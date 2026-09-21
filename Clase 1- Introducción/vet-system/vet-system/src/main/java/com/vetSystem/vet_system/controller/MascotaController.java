package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.entity.Mascota;
import com.vetSystem.vet_system.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
@Tag(name = "Mascotas", description = "Operaciones relacionadas con las mascotas de la clínica veterinaria")
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping
    @Operation(summary = "Listar mascotas", description = "Obtiene la lista completa de mascotas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de mascotas obtenida correctamente")
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        return ResponseEntity.ok(mascotaService.getAllMascotas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener mascota por ID", description = "Busca una mascota específica mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Mascota encontrada correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado")
    public ResponseEntity<Mascota> getMascotaById(@Parameter(description = "ID de la mascota", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.getMascotaBYId(id)
        );
    }

    @PostMapping
    @Operation(summary = "Crear mascota", description = "Registra una nueva mascota en la clínica.")
    @ApiResponse(responseCode = "201", description = "Mascota creada correctamente")
    @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
    public ResponseEntity<Mascota> createMascota(@Parameter(description = "ID del dueño al que pertenece la mascota", example = "1") @RequestParam Long duenioId, @Valid @RequestBody Mascota mascota) {

        Mascota nueva = mascotaService.createMascota(duenioId, mascota);

        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);}

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mascota", description = "Actualiza los datos de una mascota existente.")
    @ApiResponse(responseCode = "200", description = "Mascota actualizada correctamente")
    @ApiResponse(responseCode = "400",description = "Los datos enviados no son válidos")
    @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado")
    public ResponseEntity<Mascota> updateMascota(@Parameter(description = "ID de la mascota", example = "1")@PathVariable Long id, @Valid @RequestBody Mascota mascota) {

        return ResponseEntity.ok(mascotaService.updateMascota(id, mascota));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota existente mediante su identificador.")
    @ApiResponse(responseCode = "204", description = "Mascota eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado")
    public ResponseEntity<Void> deleteMascota(@Parameter(description = "ID de la mascota", example = "1")@PathVariable Long id) {

        mascotaService.deleteMascota(id);

        return ResponseEntity.noContent().build();
    }
}