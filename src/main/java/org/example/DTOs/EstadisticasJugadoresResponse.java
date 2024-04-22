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
public class EstadisticasJugadoresResponse {
    private String[] names;
    private String[] namesPorcentaje;
    private int[] partidasGanadas;
    private int[] partidasPerdidas;
    private double[] porcentajeVictorias;
}