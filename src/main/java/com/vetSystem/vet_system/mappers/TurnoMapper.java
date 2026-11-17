package com.vetSystem.vet_system.mappers;

import com.vetSystem.vet_system.dto.TurnoResponseDTO;
import com.vetSystem.vet_system.entity.Turno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TurnoMapper {

    @Mapping(source = "mascota.id", target = "mascotaId")
    @Mapping(source = "mascota.nombre", target = "mascotaNombre")
    @Mapping(source = "veterinario.id", target = "veterinarioId")
    @Mapping(source = "veterinario.nombre", target = "veterinarioNombre")
    TurnoResponseDTO toDTO(Turno turno);
}
