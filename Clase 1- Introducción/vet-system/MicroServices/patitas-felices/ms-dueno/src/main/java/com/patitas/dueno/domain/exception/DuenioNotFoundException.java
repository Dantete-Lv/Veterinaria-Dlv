package com.patitas.dueno.domain.exception;

public class DuenioNotFoundException extends RuntimeException {
    public DuenioNotFoundException(Long id) {
        super("no se encontró el dueño con el id " + id);
    }
}
