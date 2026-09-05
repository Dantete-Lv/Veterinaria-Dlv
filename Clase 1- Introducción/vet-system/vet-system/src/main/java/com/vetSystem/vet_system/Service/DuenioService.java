package com.vetSystem.vet_system.Service;

import com.vetSystem.vet_system.Exception.ResourceNotFoundException;
import com.vetSystem.vet_system.Entity.Duenio;
import com.vetSystem.vet_system.Repository.DuenioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DuenioService {

    private final DuenioRepository duenioRepository;

    public List<Duenio> getAllDuenios(){
     return duenioRepository.findAll();
    }

    public Duenio getDuenioById(Long id){
        return duenioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Duenio", id));
    }

    public Duenio createDuenio(Duenio duenio){
        if(duenioRepository.existsByDni(duenio.getDni())){
            throw new RuntimeException("Ya exisate un dueño don DNI: " + duenio.getDni());
        }
        return duenioRepository.save(duenio);
    }

    public Duenio updateDuenio(Long id, Duenio duenioActualizado){
        Duenio duenio = getDuenioById(id);
        duenio.setNombre(duenioActualizado.getNombre());
        duenio.setApellido(duenioActualizado.getApellido());
        duenio.setTelefono(duenioActualizado.getTelefono());
        duenio.setEmail(duenioActualizado.getEmail());
        return duenioRepository.save(duenio);
    }

    public void deleteDuenio(Long id){
        Duenio duenio = getDuenioById(id);
        duenioRepository.delete(duenio);
    }

}
