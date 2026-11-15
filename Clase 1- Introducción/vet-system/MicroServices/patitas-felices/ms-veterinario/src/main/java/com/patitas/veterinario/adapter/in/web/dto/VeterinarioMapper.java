package com.patitas.veterinario.adapter.in.web.dto;

import com.patitas.veterinario.domain.model.Veterinario;

public final class VeterinarioMapper {

    private VeterinarioMapper() {

    }

    public static Veterinario toEntity(VeterinarioRequest req) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(req.getNombre());
        veterinario.setApellido(req.getApellido());
        veterinario.setMatricula(req.getMatricula());
        veterinario.setEspecialidad(req.getEspecialidad());
        veterinario.setEmail(req.getEmail());
        return veterinario;
    }

    public static VeterinarioResponse toResponse(Veterinario veterinario) {
        return new VeterinarioResponse(
                veterinario.getId(),
                veterinario.getNombre(),
                veterinario.getApellido(),
                veterinario.getMatricula(),
                veterinario.getEspecialidad(),
                veterinario.getEmail()
        );
    }
}
