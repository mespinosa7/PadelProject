package org.example.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Clase que representa la solicitud de creacion de nueva pareja.
 */
@Getter
@Setter
@NoArgsConstructor
public class NewParejaRequest {
    /**
     * Nombre del usuario de la primera pareja.
     */
    @NotBlank
    private String username1;
    /**
     * Nombre de usuario de la segunda pareja.
     */
    @NotBlank
    private String username2;

}