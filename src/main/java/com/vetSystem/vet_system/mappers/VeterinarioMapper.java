package com.vetSystem.vet_system.mappers;

import com.vetSystem.vet_system.entity.Veterinario;
import com.vetSystem.vet_system.dto.VeterinarioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeterinarioMapper {

    VeterinarioDTO toDTO(Veterinario veterinario);

    Veterinario toEntity(VeterinarioDTO dto);

}
