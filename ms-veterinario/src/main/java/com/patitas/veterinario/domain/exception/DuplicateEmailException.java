package com.patitas.veterinario.domain.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Ya existe un veterinario con el email " + email);
    }
}