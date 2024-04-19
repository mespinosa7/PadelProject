package org.example.mapper;

import org.example.DTOs.EstadisticasJugadoresResponse;
import org.example.DTOs.EstadisticasParejasResponse;
import org.example.DTOs.ParejaDTO;
import org.example.Model.Pareja;
import org.example.Model.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase que se encarga de mapear (convertir) un objeto de tipo Pareja a un objeto de tipo ParejaDTO.
 */
public class ParejaMapper {
    /**
     * Convierte un objeto Pareja en un objeto ParejaDTO.
     *
     * @param pareja El objeto Pareja a ser mapeado.
     * @return El objeto ParejaDTO mapeado.
     */
    public ParejaDTO mapParejaToParejaDto(Pareja pareja){
        ParejaDTO parejaDto=new ParejaDTO();
        parejaDto.setId(Integer.parseInt(pareja.getId().toString()));
        parejaDto.setNombrePareja(pareja.getJugador1().getName() + " - " +pareja.getJugador2().getName());
        parejaDto.setJugador1(Integer.parseInt(pareja.getJugador1().getId().toString()));
        parejaDto.setJugador2(Integer.parseInt(pareja.getJugador2().getId().toString()));
        parejaDto.setP_ganadas(pareja.getPartidasGanadas().size());
        parejaDto.setP_jugadas(pareja.getPartidasPerdidas().size() + pareja.getPartidasGanadas().size());
        parejaDto.setP_perdidas(pareja.getPartidasPerdidas().size());
        parejaDto.setNombre_jugador1(pareja.getJugador1().getName());
        parejaDto.setNombre_jugador2(pareja.getJugador2().getName());

        return parejaDto;

    }

    public EstadisticasParejasResponse getEstadisticasParejasReponse(List<Pareja> all) {
        EstadisticasParejasResponse estadisticasParejasResponse = new EstadisticasParejasResponse();
        Map<String,List<Integer>> partidasGanadasPerdidas=all.stream()
                .collect(Collectors.toMap(
                        pareja->pareja.getJugador1().getName() + " - " +pareja.getJugador2().getName(),
                        pareja -> Arrays.asList(OptionalInt.of(pareja.getPartidasGanadas().size()).orElse(0), OptionalInt.of(pareja.getPartidasPerdidas().size()).orElse(0))
                ));
        Map<String,Double> porcentajeVictorias=all.stream()
                .collect(Collectors.toMap(
                        pareja->pareja.getJugador1().getName() + " - " +pareja.getJugador2().getName(),
                        pareja->calcularPorcentajeVictorias(pareja)
                ));
        Map<String, List<Integer>> partidasGanadasPerdidasOrdenadoPorGanadas = partidasGanadasPerdidas.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((lista1, lista2) -> lista2.get(0).compareTo(lista1.get(0))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        Map<String, Double> porcentajeVictoriasOrdenado = porcentajeVictorias.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        estadisticasParejasResponse.setPartidasGanadasPerdidas(partidasGanadasPerdidasOrdenadoPorGanadas);
        estadisticasParejasResponse.setPorcentajesVicotrias(porcentajeVictoriasOrdenado);
        return estadisticasParejasResponse;

    }
    public EstadisticasParejasResponse getEstadisticasParejasReponsePorJugador(List<Pareja> all,String name) {
        EstadisticasParejasResponse estadisticasParejasResponse = new EstadisticasParejasResponse();
        Map<String,List<Integer>> partidasGanadasPerdidas=all.stream()
                .collect(Collectors.toMap(
                        pareja->pareja.getJugador1().getName().equals(name)?pareja.getJugador2().getName():pareja.getJugador1().getName(),
                        pareja -> Arrays.asList(OptionalInt.of(pareja.getPartidasGanadas().size()).orElse(0), OptionalInt.of(pareja.getPartidasPerdidas().size()).orElse(0))
                ));
        Map<String,Double> porcentajeVictorias=all.stream()
                .collect(Collectors.toMap(
                        pareja->pareja.getJugador1().getName().equals(name)?pareja.getJugador2().getName():pareja.getJugador1().getName(),
                        pareja->calcularPorcentajeVictorias(pareja)
                ));
        Map<String, List<Integer>> partidasGanadasPerdidasOrdenadoPorGanadas = partidasGanadasPerdidas.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((lista1, lista2) -> lista2.get(0).compareTo(lista1.get(0))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        Map<String, Double> porcentajeVictoriasOrdenado = porcentajeVictorias.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        estadisticasParejasResponse.setPartidasGanadasPerdidas(partidasGanadasPerdidasOrdenadoPorGanadas);
        estadisticasParejasResponse.setPorcentajesVicotrias(porcentajeVictoriasOrdenado);
        return estadisticasParejasResponse;

    }

    public double calcularPorcentajeVictorias(Pareja pareja) {
        int partidasGanadas = OptionalInt.of(pareja.getPartidasGanadas().size()).orElse(0);
        int partidasPerdidas = OptionalInt.of(pareja.getPartidasPerdidas().size()).orElse(0);

        if (partidasGanadas + partidasPerdidas == 0) {
            return 0.0;
        }

        return (double) partidasGanadas / (partidasGanadas + partidasPerdidas) * 100.0;
    }
}
