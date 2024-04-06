package org.example.mapper;

import org.example.DTOs.JugadorDTO;
import org.example.DTOs.ParejaDTO;
import org.example.Model.Pareja;
import org.example.Model.User;

import java.util.Collections;
import java.util.Objects;
import java.util.OptionalInt;

public class JugadorMapper {

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

    private Integer getPartidasGanadas(User jugador) {
        int partidasGanadasJugador1 = jugador.getParejasComoJugador1().stream()
                .mapToInt(e-> Objects.requireNonNullElse(e.getPartidasGanadas(), Collections.emptyList()).size()).sum();
        int partidasGanadasJugador2=jugador.getParejasComoJugador2().stream()
                .mapToInt(e->Objects.requireNonNullElse(e.getPartidasGanadas(), Collections.emptyList()).size()).sum();

        int totalPartidasGanadas = partidasGanadasJugador1 + partidasGanadasJugador2;

        // Devolver el total de partidas ganadas o 0 si es que no hay ninguna
        return OptionalInt.of(totalPartidasGanadas).orElse(0);
    }

    private Integer getPartidasPerdidas(User jugador) {
        int partidasPerdidasJugador1 = jugador.getParejasComoJugador1().stream()
                .mapToInt(e->Objects.requireNonNullElse(e.getPartidasPerdidas(), Collections.emptyList()).size()).sum();
        int partidasPerdidasJugador2=jugador.getParejasComoJugador2().stream()
                .mapToInt(e->Objects.requireNonNullElse(e.getPartidasPerdidas(), Collections.emptyList()).size()).sum();

        int totalPartidasPerdidas = partidasPerdidasJugador1 + partidasPerdidasJugador2;

        // Devolver el total de partidas ganadas o 0 si es que no hay ninguna
        return OptionalInt.of(totalPartidasPerdidas).orElse(0);
    }
}
