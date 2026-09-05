package com.vetSystem.vet_system.Service;

import com.vetSystem.vet_system.Exception.ResourceNotFoundException;
import com.vetSystem.vet_system.Entity.Duenio;
import com.vetSystem.vet_system.Entity.Mascota;
import com.vetSystem.vet_system.Repository.DuenioRepository;
import com.vetSystem.vet_system.Repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    private final DuenioRepository duenioRepository;

    public List<Mascota> getAllMascotas(){
        return mascotaRepository.findAll();
    }

    public Mascota getMascotaBYId(Long id){
        return mascotaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mascota", id));
    }

    @Transactional
    public List<Mascota> getMascotaByDuenio(Long duenioId){
        if(!duenioRepository.existsById(duenioId)){
            throw new ResourceNotFoundException("Duenio", duenioId);
        }
        return mascotaRepository.findByDuenioId(duenioId);
    }

    @Transactional
    public Mascota createMascota(Long duenioId, Mascota mascota){
        Duenio duenio = duenioRepository.findById(duenioId).orElseThrow(() -> new ResourceNotFoundException("Duenio", duenioId));
        mascota.setDuenio(duenio);
        return mascotaRepository.save(mascota);
    }

    @Transactional
    public Mascota updateMascota(Long id, Mascota datos){
        Mascota mascota = getMascotaBYId(id);
        mascota.setNombre(datos.getNombre());
        mascota.setEspecie(datos.getEspecie());
        mascota.setRaza(datos.getRaza());
        mascota.setFechaNacimiento(datos.getFechaNacimiento());
        return mascotaRepository.save(mascota);
    }

    public void deleteMascota(Long id){
        Mascota mascota = getMascotaBYId(id);
        mascotaRepository.delete(mascota);
    }


}
