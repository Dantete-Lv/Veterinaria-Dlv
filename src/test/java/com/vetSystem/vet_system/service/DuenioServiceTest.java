package com.vetSystem.vet_system.service;

import com.vetSystem.vet_system.dto.DuenioDTO;
import com.vetSystem.vet_system.entity.Duenio;
import com.vetSystem.vet_system.exception.DuplicateResourceException;
import com.vetSystem.vet_system.exception.ResourceNotFoundException;
import com.vetSystem.vet_system.mappers.DuenioMapper;
import com.vetSystem.vet_system.repository.DuenioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DuenioServiceTest {

    @Mock
    private DuenioRepository duenioRepository;

    @Mock
    private DuenioMapper duenioMapper;

    @InjectMocks
    private DuenioService duenioService;

    @Test
    void getAllDuenios_conDatos(){

        Duenio duenio = new Duenio();

        DuenioDTO duenioDTO = new DuenioDTO();

        when(duenioRepository.findAll()).thenReturn(List.of(duenio));

        when(duenioMapper.toDTO(duenio)).thenReturn(duenioDTO);

        List<DuenioDTO> resultado = duenioService.getAllDuenios();

        assertEquals(1, resultado.size());
    }

    @Test
    void getAllDueniosById_cuandoExiste(){

        Duenio duenio = new Duenio();
        duenio.setNombre("Dante");

        DuenioDTO duenioDTO = new DuenioDTO();
        duenioDTO.setNombre("Dante");

        when(duenioRepository.findById(1L)).thenReturn(Optional.of(duenio));

        when(duenioMapper.toDTO(duenio)).thenReturn(duenioDTO);

        DuenioDTO resultado = duenioService.getDuenioById(1L);

        assertEquals("Dante", resultado.getNombre());
    }

    @Test
    void getDuenoById_cuandoNoExiste() {

        when(duenioRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> duenioService.getDuenioById(1L)
        );
    }

    @Test
    void createDueno_exitoso() {

        DuenioDTO dto = new DuenioDTO();
        dto.setNombre("Dante");
        dto.setApellido("Apellido");
        dto.setDni("12345678");

        Duenio duenio = new Duenio();
        duenio.setNombre("Dante");

        Duenio duenioGuardado = new Duenio();
        duenioGuardado.setNombre("Dante");

        DuenioDTO resultadoDTO = new DuenioDTO();
        resultadoDTO.setNombre("Dante");

        when(duenioRepository.existsByDni(dto.getDni()))
                .thenReturn(false);

        when(duenioMapper.toEntity(dto))
                .thenReturn(duenio);

        when(duenioRepository.save(duenio))
                .thenReturn(duenioGuardado);

        when(duenioMapper.toDTO(duenioGuardado))
                .thenReturn(resultadoDTO);

        DuenioDTO resultado = duenioService.createDuenio(dto);

        assertEquals("Dante", resultado.getNombre());

        verify(duenioRepository).save(duenio);
    }

    @Test
    void createDueno_cuandoDniDuplicado() {

        DuenioDTO dto = new DuenioDTO();
        dto.setDni("12345678");

        when(duenioRepository.existsByDni(dto.getDni()))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> duenioService.createDuenio(dto)
        );

        verify(duenioRepository, never()).save(any(Duenio.class));
    }

}
