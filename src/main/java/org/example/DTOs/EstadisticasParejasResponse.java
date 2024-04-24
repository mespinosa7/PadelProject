package org.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * Clase que representa la respuesta de las estadísticas de las parejas de jugadores.
 */
public class EstadisticasParejasResponse {
    /**
     * Array de nombres de parejas de jugadores.
     */
    private String[] names;
    /**
     * Array de nombres de parejas de jugadores con su porcentaje de victorias.
     */
    private String[] namesPorcentaje;
    /**
     * Array de número de partidas ganadas por cada pareja de jugadores.
     */
    private int[] partidasGanadas;
    /**
     * Array de número de partidas perdidas por cada pareja de jugadores.
     */
    private int[] partidasPerdidas;
    /**
     * Array de porcentaje de victorias de cada pareja de jugadores.
     */
    private double[] porcentajeVictorias;
}