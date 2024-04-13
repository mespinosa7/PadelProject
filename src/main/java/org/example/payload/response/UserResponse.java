package org.example.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.Model.Partida;
import org.example.Model.Role;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Clase que representa la respuesta de una solicitud para ver partidas.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    /**
     * Identificador único del usuario.
     */
    private Long id;
    /**
     * Nombre del usuario.
     */
    private String name;
    /**
     * Username, que es el nombre que utiliza el ususario para registrarse
     */
    private String username;
    /**
     * Rol del usuario. En este caso, hemos decidido que todos tienen el mismo, aunque eso podría cambiar
     */
    private Role role;
    /**
     * Número de teléfono del usuario.
     */
    private String telefono;
    /**
     * Correo electrónico del usuario.
     */
    private String email;
    /**
     * Edad del usuario.
     */
    private int edad;
    /**
     * Apellidos del usuario.
     */
    private String apellidos;
    /**
     * Foto del usuario en formato byte.
     */
    private byte[] foto;

}