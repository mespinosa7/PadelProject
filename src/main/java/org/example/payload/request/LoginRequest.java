package org.example.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
/**
 * Clase que representa la solicitud de inicio de sesión.
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    /**
     * Nombre de usuario proporcionado en la solicitud de inicio de sesión.
     */
    @NotBlank
    private String username;
    /**
     * Contraseña proporcionada en la solicitud de inicio de sesión.
     */
    @NotBlank
    private String password;

}