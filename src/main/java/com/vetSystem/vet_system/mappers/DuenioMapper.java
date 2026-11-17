package com.vetSystem.vet_system.mappers;

import com.vetSystem.vet_system.entity.Duenio;
import com.vetSystem.vet_system.dto.DuenioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DuenioMapper {

    DuenioDTO toDTO(Duenio duenio);

    @Mapping(target = "mascotas", ignore = true)
    Duenio toEntity(DuenioDTO dto);
}