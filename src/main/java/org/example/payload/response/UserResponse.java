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
    private Long id;

    private String name;

    private String username;


    private Role role;


    private String telefono;

    private String email;

    private int edad;

    private String apellidos;

    private byte[] foto;

}