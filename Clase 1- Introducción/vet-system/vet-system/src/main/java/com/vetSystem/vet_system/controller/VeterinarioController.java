package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.dto.VeterinarioDTO;
import com.vetSystem.vet_system.service.VeterinarioService;
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
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
@Tag(name = "Veterinarios", description = "Operaciones relacionadas con los veterinarios de la clínica veterinaria")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @GetMapping
    @Operation(summary = "Listar veterinarios", description = "Obtiene la lista completa de veterinarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de veterinarios obtenida correctamente")
    public ResponseEntity<List<VeterinarioDTO>> getAllVeterinarios(){
        return ResponseEntity.ok(veterinarioService.getAllVeterinarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener veterinario por ID", description = "Busca un veterinario específico mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Veterinario encontrado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado")
    public ResponseEntity<VeterinarioDTO> getVeterinarioById(@Parameter(description = "ID del veterinario", example = "1") @PathVariable Long id)  {

        return ResponseEntity.ok(
                veterinarioService.getVeterinarioById(id)
        );
    }

    @PostMapping
    @Operation(summary = "Crear veterinario", description = "Registra un nuevo veterinario en la clínica.")
    @ApiResponse(responseCode = "201", description = "Veterinario creado correctamente")
    @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
    @ApiResponse(responseCode = "409", description = "Ya existe un Veterinario con la matrícula indicada")
    public ResponseEntity<VeterinarioDTO> createVeterinario(@Valid @RequestBody VeterinarioDTO dto) {

        VeterinarioDTO nuevo = veterinarioService.createVeterinario(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar veterinario", description = "Actualiza los datos de un veterinario existente.")
    @ApiResponse(responseCode = "200", description = "Veterinario actualizado correctamente")
    @ApiResponse(responseCode = "400",description = "Los datos enviados no son válidos")
    @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con ID indicado")
    public ResponseEntity<VeterinarioDTO> updateVeterinario(@Parameter(description = "ID del veterinario", example = "1") @PathVariable Long id, @Valid @RequestBody VeterinarioDTO dto) {

        return ResponseEntity.ok(veterinarioService.updateVeterinario(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar veterinario", description = "Elimina un veterinario existente mediante su identificador.")
    @ApiResponse(responseCode = "204", description = "Veterinario eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado")
    public ResponseEntity<Void> deleteVeterinario(@Parameter(description = "ID del veterinario", example = "1") @PathVariable Long id) {

        veterinarioService.deleteVeterinario(id);

        return ResponseEntity.noContent().build();
    }
}