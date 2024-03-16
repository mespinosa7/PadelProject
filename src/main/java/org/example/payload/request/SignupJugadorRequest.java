package org.example.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que representa la solicitud de registro de un nuevo jugador.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupJugadorRequest {
    /**
     * Nombre de usuario proporcionado en la solicitud de registro.
     */
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotNull
    private String telefono;
    @NotNull
    private String email;
    @NotNull
    private int edad;
    @NotNull
    private String apellidos;
    /**
     * Contraseña proporcionada en la solicitud de registro.
     */
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    /**
     * Nombre del jugador proporcionado en la solicitud de registro.
     */
    @NotBlank
    private String name;


}
