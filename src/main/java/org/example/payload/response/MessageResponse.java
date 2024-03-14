package org.example.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Clase que representa una respuesta simple con un mensaje.
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
    /**
     * Mensaje de la respuesta.
     */
    private String message;
}