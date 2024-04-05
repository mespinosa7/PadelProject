package org.example.DTOs;

import lombok.*;
import org.example.Model.Role;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JugadorDTO {
    private Long id;

    private String name;

    private String username;

    private int edad;

    private String apellidos;

    private Integer partidasGanadas;
    private Integer partidasPerdidas;
    private Integer partidasJugadas;
}
