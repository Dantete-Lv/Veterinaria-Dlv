package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.dto.DuenioDTO;
import com.vetSystem.vet_system.service.DuenioService;
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
@RequestMapping("/api/duenios")
@RequiredArgsConstructor
@Tag(name = "Dueños", description = "Operaciones relacionadas con los dueños de la clínica veterinaria")
public class DuenioController {

    private final DuenioService duenioService;
    private final MascotaService mascotaService;

    @GetMapping
    @Operation(summary = "Listar dueños", description = "Obtiene la lista completa de dueños registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de dueños obtenida correctamente")
    public ResponseEntity<List<DuenioDTO>> getAllDuenios() {
        return ResponseEntity.ok(duenioService.getAllDuenios()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener dueño por ID", description = "Busca un dueño específico mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Dueño encontrado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el ID indicado")
    public ResponseEntity<DuenioDTO> getDuenioById(@Parameter(description = "ID del dueño", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(duenioService.getDuenioById(id));
    }

    @PostMapping
    @Operation(summary = "Crear dueño", description = "Registra un nuevo dueño en la clínica.")
    @ApiResponse(responseCode = "201", description = "Dueño creado correctamente")
    @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
    @ApiResponse(responseCode = "409", description = "Ya existe un dueño con el DNI indicado")
    public ResponseEntity<DuenioDTO> createDuenio(@Valid @RequestBody DuenioDTO dto) {
        DuenioDTO nuevo = duenioService.createDuenio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar dueño", description = "Actualiza los datos de un dueño existente.")
    @ApiResponse(responseCode = "200", description = "Dueño actualizado correctamente")
    @ApiResponse(responseCode = "400",description = "Los datos enviados no son válidos")
    @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el ID indicado")
    public ResponseEntity<DuenioDTO> updateDuenio(@Parameter(description = "ID del dueño", example = "1") @PathVariable Long id, @Valid @RequestBody DuenioDTO dto) {
        return ResponseEntity.ok(duenioService.updateDuenio(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar dueño", description = "Elimina un dueño existente mediante su identificador.")
    @ApiResponse(responseCode = "204", description = "Dueño eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el ID indicado")
    public ResponseEntity<Void> deleteDuenio(@Parameter(description = "ID del dueño", example = "1") @PathVariable Long id) {
        duenioService.deleteDuenio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/mascotas")
    @Operation(summary = "Listar mascotas de un dueño", description = "Obtiene las mascotas asociadas al dueño indicado.")
    @ApiResponse(responseCode = "200", description = "Mascotas obtenidas correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró el dueño indicado")
    public ResponseEntity<?> getMascotaByDuenio(@Parameter(description = "ID del dueño", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.getMascotaByDuenio(id)
        );
    }
}