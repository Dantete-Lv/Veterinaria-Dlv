package com.patitas.turno.adapter.in.web.dto;

import com.patitas.turno.domain.model.Turno;


public final class TurnoMapper {

    private TurnoMapper() {

    }

    public static Turno toEntity(TurnoRequest req) {
        Turno turno = new Turno();
        turno.setFechaHora(req.getFechaHora());
        turno.setMascotaId(req.getMascotaId());
        turno.setVeterinarioId(req.getVeterinarioId());
        turno.setMotivo(req.getMotivo());
        return turno;
    }

    public static TurnoResponse toResponse(Turno turno) {
        return new TurnoResponse(
                turno.getId(),
                turno.getFechaHora(),
                turno.getMascotaId(),
                turno.getVeterinarioId(),
                turno.getMotivo(),
                turno.getEstado()
        );
    }
}
