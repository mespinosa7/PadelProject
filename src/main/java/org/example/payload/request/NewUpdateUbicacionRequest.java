package org.example.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Clase que representa la solicitud de actualización de un jugador.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewUpdateUbicacionRequest {
    /**
     * Nombre de la ubicacion
     */
    @NotNull
    private String name;
    /**
     * Codigo postal
     */
    @NotNull
    private String codigo_postal;
    /**
     * Direccion (tipo C/Lepanto nª 50)
     */
    @NotNull
    private String direccion;
}
