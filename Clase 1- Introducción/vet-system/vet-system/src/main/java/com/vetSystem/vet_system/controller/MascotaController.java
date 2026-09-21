package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.entity.Mascota;
import com.vetSystem.vet_system.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        return ResponseEntity.ok(mascotaService.getAllMascotas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        return ResponseEntity.ok(
                mascotaService.getMascotaBYId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Mascota> createMascota(
            @RequestParam Long duenioId,
            @Valid @RequestBody Mascota mascota) {

        Mascota nueva = mascotaService.createMascota(
                duenioId,
                mascota
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(
            @PathVariable Long id,
            @Valid @RequestBody Mascota mascota) {

        return ResponseEntity.ok(
                mascotaService.updateMascota(id, mascota)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {

        mascotaService.deleteMascota(id);

        return ResponseEntity.noContent().build();
    }
}