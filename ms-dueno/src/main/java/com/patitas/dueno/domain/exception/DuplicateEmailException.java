package com.patitas.dueno.domain.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Ya existe el dueño con ese email " + email);
    }
}