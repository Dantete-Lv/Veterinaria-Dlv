package com.patitas.mascota.adapter.in.web.dto;

import com.patitas.mascota.domain.model.Mascota;

public final class MascotaMapper {

    private MascotaMapper() {

    }

    public static Mascota toEntity(MascotaRequest req) {
        Mascota mascota = new Mascota();
        mascota.setNombre(req.getNombre());
        mascota.setEspecie(req.getEspecie());
        mascota.setRaza(req.getRaza());
        mascota.setFechaNacimiento(req.getFechaNacimiento());
        mascota.setDuenioId(req.getDuenioId());
        return mascota;
    }

    public static MascotaResponse toResponse(Mascota mascota) {
        return new MascotaResponse(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getFechaNacimiento(),
                mascota.getDuenioId()
        );
    }
}
