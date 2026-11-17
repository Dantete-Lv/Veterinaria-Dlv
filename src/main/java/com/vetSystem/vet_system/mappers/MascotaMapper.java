package com.vetSystem.vet_system.mappers;

import com.vetSystem.vet_system.entity.Mascota;
import com.vetSystem.vet_system.dto.MascotaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MascotaMapper {

    @Mapping(source = "duenio.id", target = "duenioId")
    @Mapping(source = "duenio.nombre", target = "duenioNombre")
    MascotaDTO toDTO(Mascota mascota);
}
