package org.example.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private Long user1Id;
    /**
     * Nombre de usuario de la segunda pareja.
     */
    @NotNull
    private Long user2Id;

}