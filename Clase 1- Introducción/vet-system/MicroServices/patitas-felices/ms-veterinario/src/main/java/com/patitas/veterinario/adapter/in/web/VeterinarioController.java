
package com.patitas.veterinario.adapter.in.web;

import com.patitas.veterinario.adapter.in.web.dto.VeterinarioMapper;
import com.patitas.veterinario.adapter.in.web.dto.VeterinarioRequest;
import com.patitas.veterinario.adapter.in.web.dto.VeterinarioResponse;
import com.patitas.veterinario.domain.model.Veterinario;
import com.patitas.veterinario.domain.port.in.VeterinarioService;
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
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
@Tag(name = "Veterinarios", description = "Operaciones para gestionar veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @Operation(summary = "Listar todos los veterinarios", description = "Obtiene el listado completo de veterinarios registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<VeterinarioResponse>> listarTodos() {
        List<VeterinarioResponse> respuesta = veterinarioService.listarTodos().stream().map(VeterinarioMapper::toResponse).toList();
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar veterinario por ID", description = "Obtiene los datos de un veterinario utilizando su identificador."
    )
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Veterinario encontrado correctamente"), @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado")})
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> buscarPorId(@Parameter(description = "Identificador del veterinario", example = "1") @PathVariable Long id) {
        Veterinario veterinario = veterinarioService.buscarPorId(id);
        return ResponseEntity.ok(VeterinarioMapper.toResponse(veterinario));
    }

    @Operation(summary = "Registrar un veterinario", description = "Registra un nuevo veterinario en el sistema.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Veterinario registrado correctamente"), @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"), @ApiResponse(responseCode = "409", description = "Ya existe un veterinario con la matrícula o email indicado")})
    @PostMapping
    public ResponseEntity<VeterinarioResponse> registrar(@Valid @RequestBody VeterinarioRequest request) {
        Veterinario creado = veterinarioService.registrar(VeterinarioMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(VeterinarioMapper.toResponse(creado));
    }

    @Operation(summary = "Actualizar un veterinario", description = "Actualiza los datos de un veterinario existente utilizando su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Veterinario actualizado correctamente"), @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"), @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado"), @ApiResponse(responseCode = "409", description = "La matrícula o el email ya pertenecen a otro veterinario")})
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> actualizar(@Parameter(description = "Identificador del veterinario", example = "1") @PathVariable Long id, @Valid @RequestBody VeterinarioRequest request) {
        Veterinario actualizado = veterinarioService.actualizar(id, VeterinarioMapper.toEntity(request));
        return ResponseEntity.ok(VeterinarioMapper.toResponse(actualizado));
    }

    @Operation(summary = "Eliminar un veterinario", description = "Elimina permanentemente un veterinario utilizando su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Veterinario eliminado correctamente"), @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@Parameter(description = "Identificador del veterinario", example = "1") @PathVariable Long id) {
        veterinarioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}