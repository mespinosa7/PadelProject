package org.example.mapper;

import org.example.DTOs.EstadisticasJugadoresResponse;
import org.example.DTOs.JugadorDTO;
import org.example.Model.Pareja;
import org.example.Model.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase que se encarga de mapear un objeto de tipo User a un objeto de tipo JugadorDTO.
 */
public class JugadorMapper {
    /**
     * Convierte un objeto User en un objeto JugadorDTO.
     *
     * @param jugador El objeto User a ser mapeado.
     * @return El objeto JugadorDTO mapeado.
     */
    public JugadorDTO mapJugadorToJugadorDTO(User jugador){
        JugadorDTO jugadorDTO=JugadorDTO.builder()
                .id(jugador.getId())
                .edad(jugador.getEdad())
                .name(jugador.getName())
                .apellidos(jugador.getApellidos())
                .username(jugador.getUsername())
                .partidasGanadas(getPartidasGanadas(jugador))
                .partidasPerdidas(getPartidasPerdidas(jugador)).build();
        jugadorDTO.setPartidasJugadas(jugadorDTO.getPartidasGanadas()+ jugadorDTO.getPartidasPerdidas());


        return jugadorDTO;

    }
    public EstadisticasJugadoresResponse getEstadisticasJugadoresResponse(List<User> all) {
        EstadisticasJugadoresResponse estadisticasJugadoresResponse = new EstadisticasJugadoresResponse();
        Map<String,List<Integer>> partidasGanadasPerdidas=all.stream()
                .collect(Collectors.toMap(
                        User::getUsername,
                        user -> Arrays.asList(getPartidasGanadas(user), getPartidasPerdidas(user))
                ));
        Map<String,Double> porcentajeVictorias=all.stream()
                .collect(Collectors.toMap(
                        User::getUsername,
                        user->calcularPorcentajeVictorias(user)
                ));
        Map<String, List<Integer>> partidasGanadasPerdidasOrdenadoPorGanadas = partidasGanadasPerdidas.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((lista1, lista2) -> lista2.get(0).compareTo(lista1.get(0))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        Map<String, Double> porcentajeVictoriasOrdenado = porcentajeVictorias.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        estadisticasJugadoresResponse.setPartidasGanadasPerdidas(partidasGanadasPerdidasOrdenadoPorGanadas);
        estadisticasJugadoresResponse.setPorcentajesVicotrias(porcentajeVictoriasOrdenado);
        return estadisticasJugadoresResponse;

    }
    /**
     * Calcula el número total de partidas ganadas por un jugador.
     *
     * @param jugador El jugador del que se desea obtener el número de partidas ganadas.
     * @return El número total de partidas ganadas por el jugador.
     */
    private Integer getPartidasGanadas(User jugador) {
        int partidasGanadasJugador1 = jugador.getParejasComoJugador1().stream()
                .mapToInt(e-> Objects.requireNonNullElse(e.getPartidasGanadas(), Collections.emptyList()).size()).sum();
        int partidasGanadasJugador2=jugador.getParejasComoJugador2().stream()
                .mapToInt(e->Objects.requireNonNullElse(e.getPartidasGanadas(), Collections.emptyList()).size()).sum();

        int totalPartidasGanadas = partidasGanadasJugador1 + partidasGanadasJugador2;

        // Devolver el total de partidas ganadas o 0 si es que no hay ninguna
        return OptionalInt.of(totalPartidasGanadas).orElse(0);
    }
    /**
     * Calcula el número total de partidas perdidas por un jugador.
     *
     * @param jugador El jugador del que se desea obtener el número de partidas perdidas.
     * @return El número total de partidas perdidas por el jugador.
     */
    private Integer getPartidasPerdidas(User jugador) {
        int partidasPerdidasJugador1 = jugador.getParejasComoJugador1().stream()
                .mapToInt(e->Objects.requireNonNullElse(e.getPartidasPerdidas(), Collections.emptyList()).size()).sum();
        int partidasPerdidasJugador2=jugador.getParejasComoJugador2().stream()
                .mapToInt(e->Objects.requireNonNullElse(e.getPartidasPerdidas(), Collections.emptyList()).size()).sum();

        int totalPartidasPerdidas = partidasPerdidasJugador1 + partidasPerdidasJugador2;

        // Devolver el total de partidas ganadas o 0 si es que no hay ninguna
        return OptionalInt.of(totalPartidasPerdidas).orElse(0);
    }

    public double calcularPorcentajeVictorias(User jugador) {
        int partidasGanadas = getPartidasGanadas(jugador);
        int partidasPerdidas = getPartidasPerdidas(jugador);

        if (partidasGanadas + partidasPerdidas == 0) {
            return 0.0;
        }

        return (double) partidasGanadas / (partidasGanadas + partidasPerdidas) * 100.0;
    }
}
