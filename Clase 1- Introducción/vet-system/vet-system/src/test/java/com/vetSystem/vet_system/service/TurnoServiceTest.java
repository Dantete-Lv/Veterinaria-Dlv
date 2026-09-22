package com.vetSystem.vet_system.service;

import com.vetSystem.vet_system.dto.TurnoRequestDTO;
import com.vetSystem.vet_system.dto.TurnoResponseDTO;
import com.vetSystem.vet_system.entity.Mascota;
import com.vetSystem.vet_system.entity.Turno;
import com.vetSystem.vet_system.entity.Veterinario;
import com.vetSystem.vet_system.exception.TurnoSuperpuestoException;
import com.vetSystem.vet_system.mappers.TurnoMapper;
import com.vetSystem.vet_system.repository.MascotaRepository;
import com.vetSystem.vet_system.repository.TurnoRepository;
import com.vetSystem.vet_system.repository.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private VeterinarioRepository veterinarioRepository;


    @Mock
    private TurnoMapper turnoMapper;

    @InjectMocks
    private TurnoService turnoService;

    @Test
    void createTurno_exitoso() {

        TurnoRequestDTO request = new TurnoRequestDTO();
        request.setMascotaId(1L);
        request.setVeterinarioId(2L);
        request.setFecha(LocalDate.of(2026, 9, 25));
        request.setHora(LocalTime.of(10, 0));
        request.setMotivo("Control");

        Mascota mascota = new Mascota();
        Veterinario veterinario = new Veterinario();

        Turno turnoGuardado = new Turno();
        TurnoResponseDTO resultadoDTO = new TurnoResponseDTO();

        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));

        when(veterinarioRepository.findById(2L)).thenReturn(Optional.of(veterinario));

        when(turnoRepository.findByVeterinarioIdAndFechaAndHora(request.getVeterinarioId(), request.getFecha(), request.getHora())).thenReturn(Optional.empty());

        when(turnoRepository.save(any(Turno.class))).thenReturn(turnoGuardado);

        when(turnoMapper.toDTO(turnoGuardado)).thenReturn(resultadoDTO);

        TurnoResponseDTO resultado = turnoService.createTurno(request);

        assertEquals(resultadoDTO, resultado);

        verify(turnoRepository).save(any(Turno.class));
    }

    @Test
    void createTurno_conSuperposicion(){

        TurnoRequestDTO request = new TurnoRequestDTO();
        request.setMascotaId(1L);
        request.setVeterinarioId(2L);
        request.setFecha(LocalDate.of(2026, 9, 25));
        request.setHora(LocalTime.of(10, 0));
        request.setMotivo("Control");

        Mascota mascota = new Mascota();
        Veterinario veterinario = new Veterinario();

        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));

        when(veterinarioRepository.findById(2L)).thenReturn(Optional.of(veterinario));

        Turno turnoConflicto = new Turno();
        turnoConflicto.setId(99L);
        turnoConflicto.setFecha(request.getFecha());
        turnoConflicto.setHora(request.getHora());

        when(turnoRepository.findByVeterinarioIdAndFechaAndHora(request.getVeterinarioId(), request.getFecha(), request.getHora())).thenReturn(Optional.of(turnoConflicto));

        assertThrows(TurnoSuperpuestoException.class, () -> turnoService.createTurno(request));

        verify(turnoRepository, never()).save(any(Turno.class));

    }

}
