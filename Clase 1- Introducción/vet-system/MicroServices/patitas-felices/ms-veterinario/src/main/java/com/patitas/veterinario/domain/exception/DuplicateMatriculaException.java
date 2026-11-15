package com.patitas.veterinario.domain.exception;

public class DuplicateMatriculaException extends RuntimeException {

    public DuplicateMatriculaException(String matricula) {
        super("Ya existe un veterinario con la matrícula " + matricula);
    }
}