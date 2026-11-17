package com.vetSystem.vet_system.service;

import com.vetSystem.vet_system.dto.DuenioDTO;
import com.vetSystem.vet_system.entity.Duenio;
import com.vetSystem.vet_system.exception.DuplicateResourceException;
import com.vetSystem.vet_system.exception.ResourceNotFoundException;
import com.vetSystem.vet_system.mappers.DuenioMapper;
import com.vetSystem.vet_system.repository.DuenioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DuenioService {

    private final DuenioRepository duenioRepository;
    private final DuenioMapper duenioMapper;

    public List<DuenioDTO> getAllDuenios() {
        return duenioRepository.findAll().stream().map(duenioMapper::toDTO).collect(Collectors.toList());
    }

    public DuenioDTO getDuenioById(Long id) {
        Duenio duenio = duenioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Duenio", id));

        return duenioMapper.toDTO(duenio);
    }

    public DuenioDTO createDuenio(DuenioDTO dto) {

        if (duenioRepository.existsByDni(dto.getDni())) {
            throw new DuplicateResourceException("Ya existe un dueño con DNI: " + dto.getDni());
        }

        Duenio duenio = duenioMapper.toEntity(dto);

        return duenioMapper.toDTO(duenioRepository.save(duenio)
        );
    }

    public DuenioDTO updateDuenio(Long id, DuenioDTO dto) {

        Duenio duenio = duenioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Duenio", id));

        duenio.setNombre(dto.getNombre());
        duenio.setApellido(dto.getApellido());
        duenio.setDni(dto.getDni());
        duenio.setTelefono(dto.getTelefono());
        duenio.setEmail(dto.getEmail());

        return duenioMapper.toDTO(duenioRepository.save(duenio)
        );
    }

    public void deleteDuenio(Long id) {

        if (!duenioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Duenio", id);
        }

        duenioRepository.deleteById(id);
    }
}