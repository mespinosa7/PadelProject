package org.example.mapper;

import org.example.DTOs.ParejaDTO;
import org.example.Model.Pareja;

public class ParejaMapper {

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
}
