package org.example.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Model.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que representa la solicitud de actualización de un jugador.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateJugadorRequest {
    /**
     * Nuevo nombre del jugador proporcionado en la solicitud de actualización. (si es diferente)
     */
    @NotBlank
    private String name;
    /**
     * Nuevos apellidos del jugador proporcionados en la solicitud de actualización.(si es diferente)
     */
    @NotBlank
    private String apellidos;
    /**
     * Nuevo teléfono del jugador proporcionado en la solicitud de actualización.(si es diferente)
     */
    @NotNull
    private String telefono;
    /**
     * Nueva edad del jugador proporcionada en la solicitud de actualización.(si es diferente)
     */
    @NotNull
    private int edad;
    /**
     * Nuevo email del jugador proporcionado en la solicitud de actualización.(si es diferente)
     */
    @NotNull
    private String email;
    /**
     * contraseña del jugador proporcionado en la solicitud de actualización.(si es diferente)
     */
    private String passwordActual;

    /**
     * Nueva contraseña del jugador proporcionado en la solicitud de actualización.(si es diferente)
     */
    private String password;
    /**
     * Nuevo role del jugador proporcionado en la solicitud de actualización.(si es diferente)
     */

    private Role role;
    /**
     * Nueva foto del jugador proporcionado en la solicitud de actualización.(si es diferente)
     */
    private byte[] foto;

}
