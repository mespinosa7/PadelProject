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
        int size = all.size();
        String[] names = new String[size];
        String[] namesPorcentaje = new String[size];
        int[] partidasGanadas = new int[size];
        int[] partidasPerdidas = new int[size];
        double[] porcentajeVictorias = new double[size];

        for (int i = 0; i < size; i++) {
            User user = all.get(i);
            names[i] = user.getName();
            partidasGanadas[i] = getPartidasGanadas(user);
            partidasPerdidas[i] = getPartidasPerdidas(user);
            namesPorcentaje[i] = user.getName();
            porcentajeVictorias[i]=calcularPorcentajeVictorias(user);
        }

        // Sort arrays
        sortArrays(names, partidasGanadas, partidasPerdidas);
        sortArraysPorcentaje(namesPorcentaje,porcentajeVictorias);

        // Set values in EstadisticasJugadoresResponse
        EstadisticasJugadoresResponse estadisticasJugadoresResponse = new EstadisticasJugadoresResponse();
        estadisticasJugadoresResponse.setNames(names);
        estadisticasJugadoresResponse.setPartidasGanadas(partidasGanadas);
        estadisticasJugadoresResponse.setPartidasPerdidas(partidasPerdidas);
        estadisticasJugadoresResponse.setNamesPorcentaje(namesPorcentaje);
        estadisticasJugadoresResponse.setPorcentajeVictorias(porcentajeVictorias);

        return estadisticasJugadoresResponse;
    }

    private void sortArrays(String[] names, int[] partidasGanadas, int[] partidasPerdidas) {
        // Sort arrays based on partidasGanadas
        for (int i = 0; i < partidasGanadas.length - 1; i++) {
            for (int j = 0; j < partidasGanadas.length - i - 1; j++) {
                if (partidasGanadas[j] < partidasGanadas[j + 1]) {
                    // Swap names
                    String tempUsername = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = tempUsername;
                    // Swap partidasGanadas
                    int tempGanadas = partidasGanadas[j];
                    partidasGanadas[j] = partidasGanadas[j + 1];
                    partidasGanadas[j + 1] = tempGanadas;
                    // Swap partidasPerdidas
                    int tempPerdidas = partidasPerdidas[j];
                    partidasPerdidas[j] = partidasPerdidas[j + 1];
                    partidasPerdidas[j + 1] = tempPerdidas;
                }
            }
        }
    }
    private void sortArraysPorcentaje(String[] namesPorcentaje, double[] porcentajeVictorias) {
        // Sort arrays based on porcentajeVictorias
        for (int i = 0; i < porcentajeVictorias.length - 1; i++) {
            for (int j = 0; j < porcentajeVictorias.length - i - 1; j++) {
                if (porcentajeVictorias[j] < porcentajeVictorias[j + 1]) {
                    // Swap names
                    String tempUsername = namesPorcentaje[j];
                    namesPorcentaje[j] = namesPorcentaje[j + 1];
                    namesPorcentaje[j + 1] = tempUsername;
                    // Swap porcentajeVictorias
                    double tempGanadas = porcentajeVictorias[j];
                    porcentajeVictorias[j] = porcentajeVictorias[j + 1];
                    porcentajeVictorias[j + 1] = tempGanadas;
                }
            }
        }
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
