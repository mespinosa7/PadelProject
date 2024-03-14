package org.example.Exceptions;

/**
 * Clase para las excepciones
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
