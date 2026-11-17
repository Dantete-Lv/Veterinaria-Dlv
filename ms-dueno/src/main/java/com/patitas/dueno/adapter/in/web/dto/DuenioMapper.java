
package com.patitas.dueno.adapter.in.web.dto;

import com.patitas.dueno.domain.model.Duenio;

public final class DuenioMapper {

    private DuenioMapper() {
    }

    public static Duenio toEntity(DuenioRequest duenioRequest) {
        Duenio duenio = new Duenio();

        duenio.setNombre(duenioRequest.getNombre());
        duenio.setApellido(duenioRequest.getApellido());
        duenio.setDni(duenioRequest.getDni());
        duenio.setTelefono(duenioRequest.getTelefono());
        duenio.setEmail(duenioRequest.getEmail());

        return duenio;
    }

    public static DuenioResponse toResponse(Duenio duenio) {
        return new DuenioResponse(
                duenio.getId(),
                duenio.getNombre(),
                duenio.getApellido(),
                duenio.getDni(),
                duenio.getTelefono(),
                duenio.getEmail()
        );
    }
}
