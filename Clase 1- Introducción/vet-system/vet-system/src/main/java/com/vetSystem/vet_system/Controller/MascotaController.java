package com.vetSystem.vet_system.Controller;

import com.vetSystem.vet_system.Entity.Duenio;
import com.vetSystem.vet_system.Exception.ResourceNotFoundException;
import com.vetSystem.vet_system.Entity.Mascota;
import com.vetSystem.vet_system.Service.MascotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.List.*;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<Mascota>> getAllMascotas(){
        return ResponseEntity.ok(mascotaService.getAllMascotas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(mascotaService.getMascotaBYId(id));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createMascota(@RequestParam Long duenioId, @RequestBody Mascota mascota){
        try{
            Mascota nueva = mascotaService.createMascota(duenioId, mascota);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMascota(@PathVariable Long id, @RequestBody Mascota mascota){
        try{
            return ResponseEntity.ok(mascotaService.updateMascota(id, mascota));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id){
        try{
            mascotaService.deleteMascota(id);
            return ResponseEntity.noContent().build();
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
