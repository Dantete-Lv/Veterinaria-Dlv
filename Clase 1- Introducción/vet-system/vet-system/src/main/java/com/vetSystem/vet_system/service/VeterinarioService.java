package com.vetSystem.vet_system.service;

import com.vetSystem.vet_system.dto.VeterinarioDTO;
import com.vetSystem.vet_system.entity.Veterinario;
import com.vetSystem.vet_system.exception.DuplicateResourceException;
import com.vetSystem.vet_system.exception.ResourceNotFoundException;
import com.vetSystem.vet_system.mappers.VeterinarioMapper;
import com.vetSystem.vet_system.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    private final VeterinarioMapper veterinarioMapper;

    public List<VeterinarioDTO> getAllVeterinarios() {
        return veterinarioRepository.findAll().stream().map(veterinarioMapper::toDTO).collect(Collectors.toList());
    }

    public VeterinarioDTO getVeterinarioById(Long id){
        Veterinario vet = veterinarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Veterinario", id));
        return veterinarioMapper.toDTO(vet);
    }

    public VeterinarioDTO createVeterinario(VeterinarioDTO dto){
        if (veterinarioRepository.existsByMatricula(dto.getMatricula())) {
            throw new DuplicateResourceException("Matricula ya registrada: " + dto.getMatricula());
        }
        Veterinario vet = veterinarioMapper.toEntity(dto);
        return veterinarioMapper.toDTO(veterinarioRepository.save(vet));
    }

    public VeterinarioDTO updateVeterinario(Long id, VeterinarioDTO dto){
        Veterinario vet = veterinarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Veterinario", id));
        vet.setNombre(dto.getNombre());
        vet.setApellido(dto.getApellido());
        vet.setEspecialidad(dto.getEspecialidad());
        return veterinarioMapper.toDTO(veterinarioRepository.save(vet));
    }

    public void deleteVeterinario(Long id){
        if(!veterinarioRepository.existsById(id)) throw new ResourceNotFoundException("Veterinario", id);
        veterinarioRepository.deleteById(id);
    }

}
