package com.patitas.dueno.adapter.in.web;

import com.patitas.dueno.adapter.in.web.dto.DuenioMapper;
import com.patitas.dueno.adapter.in.web.dto.DuenioRequest;
import com.patitas.dueno.adapter.in.web.dto.DuenioResponse;
import com.patitas.dueno.domain.port.in.DuenioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Duenios", description = "CRUD- clinica Veterinaria-Duenio")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/duenos")
public class DuenioController {


    private final DuenioService duenioService;


    @Operation(summary = "Registrar un nuevo duenio", description = "crea un nuevo duenio. Todos los valores deben estar completos ya que: el DNI debe  estar comprendido entre los 7 y 8 caracteres")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "duenio creado exitosamente"), @ApiResponse(responseCode = "400", description = "Los datos son invalidos(falla de verificacion)", content=@Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "409",description = "El DNI o correo electrónico ya registrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})

    @PostMapping
    public ResponseEntity<DuenioResponse> registrarDuenio(@Valid @RequestBody DuenioRequest duenioRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(DuenioMapper.toResponse(duenioService.registrar(DuenioMapper.toEntity(duenioRequest))));
    }

    @Operation( summary = "buscar duenio por ID", description = "devuelve los datos del duenio con el ID indicado; si no existe devuelve 404")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "duenio encontrado"), @ApiResponse(responseCode = "404",description = "no existe un duenio con ese ID", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),})

    @GetMapping("/{id}")
    public ResponseEntity<DuenioResponse> buscarPorId(@Parameter(description = "ID del duenio a buscar: ",example = "1") @PathVariable ("id") Long id){
        return ResponseEntity.ok(DuenioMapper.toResponse(duenioService.buscarPorId(id)));
    }

    @Operation( summary = "Actualizar un duenio", description = "modifica los datos del duenio con el ID indicado; si no existe devuelve 404")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "duenio modificado exitosamente"), @ApiResponse(responseCode = "400", description = "los datos enviados son inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "404", description = "no existe un duenio con ese ID", content = @Content(schema = @Schema(implementation =  ErrorResponse.class))),})
    @PutMapping("/{id}")
    public ResponseEntity<DuenioResponse> actualizarDuenio( @PathVariable Long id, @Valid @RequestBody DuenioRequest duenioRequest) {
        return ResponseEntity.ok(DuenioMapper.toResponse(duenioService.actualizar(id, DuenioMapper.toEntity(duenioRequest))));
    }


    @Operation(summary = "Listar todos los duenios que viven en la clinica", description = "Devuelve la lista completa de los duenios registrados, si no hay duenios devuelve una lista vacia con estado 200")
    @GetMapping
    public ResponseEntity<List<DuenioResponse>> buscarTodos(){
        List<DuenioResponse> respuesta= duenioService.listarTodos().stream().map(DuenioMapper::toResponse).toList();
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Eliminar duenio por ID", description = "Elimina todos los datos del duenio correspondiente al ID indicado")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Dueño eliminado exitosamente"), @ApiResponse(responseCode = "404", description = "No existe un dueño con ese ID", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        duenioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
