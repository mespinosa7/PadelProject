package org.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParejaDTO {
    Integer id;
    Integer jugador1;
    Integer jugador2;
    Integer P_jugadas;
    Integer P_ganadas;
    Integer P_perdidas;
    String nombre_jugador1;
    String nombre_jugador2;
    String nombrePareja;
}
