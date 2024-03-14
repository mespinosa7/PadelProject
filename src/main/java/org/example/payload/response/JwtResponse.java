package org.example.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Clase que representa la respuesta del servidor al iniciar sesión correctamente y generar un token JWT.
 */
@Getter
@Setter
@Builder
public class JwtResponse {
    /**
     * Tipo de token, que en este caso es "Bearer".
     */
    private final String type = "Bearer";
    /**
     * Token JWT generado para la autenticación.
     */
    private String token;
    /**
     * Identificador único del usuario autenticado.
     */
    private Long id;
    /**
     * Nombre de usuario del usuario autenticado.
     */
    private String username;
    /**
     * Lista de roles asignados al usuario autenticado.
     */
    private List<String> roles;
}
