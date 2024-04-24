package org.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * Clase que representa la respuesta de las estadísticas de los jugadores.
 */
public class EstadisticasJugadoresResponse {
    /**
     * Array de nombres de jugadores.
     */
    private String[] names;
    /**
     * Array de nombres de jugadores con su porcentaje de victorias.
     */
    private String[] namesPorcentaje;
    /**
     * Array de número de partidas ganadas por cada jugador.
     */
    private int[] partidasGanadas;
    /**
     * Array de número de partidas perdidas por cada jugador.
     */
    private int[] partidasPerdidas;
    /**
     * Array de porcentaje de victorias de cada jugador.
     */
    private double[] porcentajeVictorias;
}