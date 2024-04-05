package org.example.mapper;

import org.example.DTOs.JugadorDTO;
import org.example.DTOs.ParejaDTO;
import org.example.Model.Pareja;
import org.example.Model.User;

public class JugadorMapper {

    public JugadorDTO mapJugadorToJugadorDTO(User jugador){
        JugadorDTO jugadorDTO=new JugadorDTO();
        jugadorDTO.builder()
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
                .mapToInt(e->e.getPartidasGanadas().size()).sum();
        int partidasGanadasJugador2=jugador.getParejasComoJugador2().stream()
                .mapToInt(e->e.getPartidasGanadas().size()).sum();

        return partidasGanadasJugador1+partidasGanadasJugador2;
    }

    private Integer getPartidasPerdidas(User jugador) {
        int partidasPerdidasJugador1 = jugador.getParejasComoJugador1().stream()
                .mapToInt(e->e.getPartidasPerdidas().size()).sum();
        int partidasPerdidasJugador2=jugador.getParejasComoJugador2().stream()
                .mapToInt(e->e.getPartidasPerdidas().size()).sum();

        return partidasPerdidasJugador1+partidasPerdidasJugador2;
    }
}
