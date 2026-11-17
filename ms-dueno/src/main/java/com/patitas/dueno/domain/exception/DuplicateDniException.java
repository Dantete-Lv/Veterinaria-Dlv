package com.patitas.dueno.domain.exception;

public class DuplicateDniException extends RuntimeException {
    public DuplicateDniException(String dni) {
        super("Ya existe un dueño con ese DNI: " + dni);
    }
}
