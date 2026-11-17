package com.vetSystem.vet_system.service;

import com.vetSystem.vet_system.dto.MedicamentoRequestDTO;
import com.vetSystem.vet_system.dto.MedicamentoResponseDTO;
import com.vetSystem.vet_system.entity.Medicamento;
import com.vetSystem.vet_system.exception.ResourceNotFoundException;
import com.vetSystem.vet_system.mappers.MedicamentoMapper;
import com.vetSystem.vet_system.repository.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final MedicamentoMapper medicamentoMapper;

    public List<MedicamentoResponseDTO> getAllMedicamentos() {
        return medicamentoRepository.findAll().stream().map(medicamentoMapper::toDTO).collect(Collectors.toList());
    }

    public MedicamentoResponseDTO getMedicamentoById(Long id) {
        Medicamento medicamento = medicamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicamento", id));

        return medicamentoMapper.toDTO(medicamento);
    }

    public MedicamentoResponseDTO createMedicamento(MedicamentoRequestDTO dto) {
        Medicamento medicamento = medicamentoMapper.toEntity(dto);

        Medicamento guardado = medicamentoRepository.save(medicamento);

        return medicamentoMapper.toDTO(guardado);
    }

    public MedicamentoResponseDTO updateMedicamento(Long id, MedicamentoRequestDTO dto) {
        Medicamento medicamento = medicamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicamento", id));

        medicamento.setNombre(dto.getNombre());
        medicamento.setPrincipioActivo(dto.getPrincipioActivo());
        medicamento.setStock(dto.getStock());
        medicamento.setPrecioUnitario(dto.getPrecioUnitario());

        Medicamento actualizado = medicamentoRepository.save(medicamento);

        return medicamentoMapper.toDTO(actualizado);
    }

    public void deleteMedicamento(Long id) {
        Medicamento medicamento = medicamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicamento", id));

        medicamentoRepository.delete(medicamento);
    }
}