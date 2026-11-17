package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.dto.MedicamentoRequestDTO;
import com.vetSystem.vet_system.dto.MedicamentoResponseDTO;
import com.vetSystem.vet_system.service.MedicamentoService;
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
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
@Tag(
        name = "Medicamentos",
        description = "Operaciones relacionadas con los medicamentos de la clínica veterinaria"
)
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @GetMapping
    @Operation(summary = "Listar medicamentos", description = "Obtiene la lista completa de medicamentos registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de medicamentos obtenida correctamente")
    public ResponseEntity<List<MedicamentoResponseDTO>> getAllMedicamentos() {
        return ResponseEntity.ok(medicamentoService.getAllMedicamentos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener medicamento por ID", description = "Busca un medicamento específico mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Medicamento encontrado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un medicamento con el ID indicado")
    public ResponseEntity<MedicamentoResponseDTO> getMedicamentoById(@Parameter(description = "ID del medicamento", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(medicamentoService.getMedicamentoById(id));
    }

    @PostMapping
    @Operation(summary = "Crear medicamento", description = "Registra un nuevo medicamento en la clínica."
    )
    @ApiResponse(responseCode = "201", description = "Medicamento creado correctamente")
    @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
    public ResponseEntity<MedicamentoResponseDTO> createMedicamento(@Valid @RequestBody MedicamentoRequestDTO dto) {

        MedicamentoResponseDTO nuevo = medicamentoService.createMedicamento(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar medicamento", description = "Actualiza los datos de un medicamento existente.")
    @ApiResponse(responseCode = "200", description = "Medicamento actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
    @ApiResponse(responseCode = "404", description = "No se encontró un medicamento con el ID indicado")
    public ResponseEntity<MedicamentoResponseDTO> updateMedicamento(@Parameter(description = "ID del medicamento", example = "1") @PathVariable Long id, @Valid @RequestBody MedicamentoRequestDTO dto) {

        return ResponseEntity.ok(medicamentoService.updateMedicamento(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar medicamento", description = "Elimina un medicamento existente mediante su identificador.")
    @ApiResponse(responseCode = "204", description = "Medicamento eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un medicamento con el ID indicado")
    public ResponseEntity<Void> deleteMedicamento(@Parameter(description = "ID del medicamento", example = "1") @PathVariable Long id) {

        medicamentoService.deleteMedicamento(id);

        return ResponseEntity.noContent().build();
    }
}