package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.dto.DuenioDTO;
import com.vetSystem.vet_system.service.DuenioService;
import com.vetSystem.vet_system.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/duenios")
@RequiredArgsConstructor
public class DuenioController {

    private final DuenioService duenioService;
    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<DuenioDTO>> getAllDuenios() {
        return ResponseEntity.ok(
                duenioService.getAllDuenios()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuenioDTO> getDuenioById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                duenioService.getDuenioById(id)
        );
    }

    @PostMapping
    public ResponseEntity<DuenioDTO> createDuenio(
            @Valid @RequestBody DuenioDTO dto) {

        DuenioDTO nuevo = duenioService.createDuenio(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DuenioDTO> updateDuenio(
            @PathVariable Long id,
            @Valid @RequestBody DuenioDTO dto) {

        return ResponseEntity.ok(
                duenioService.updateDuenio(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDuenio(
            @PathVariable Long id) {

        duenioService.deleteDuenio(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/mascotas")
    public ResponseEntity<?> getMascotaByDuenio(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                mascotaService.getMascotaByDuenio(id)
        );
    }
}