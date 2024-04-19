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
public class EstadisticasParejasResponse {
    private Map<String, List<Integer>> partidasGanadasPerdidas;

    private Map<String, Double> porcentajesVicotrias;
}