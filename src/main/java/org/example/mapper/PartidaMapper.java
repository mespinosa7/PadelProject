package org.example.mapper;

import org.example.DTOs.ParejaDTO;
import org.example.DTOs.PartidaDTO;
import org.example.Model.Pareja;
import org.example.Model.Partida;

public class PartidaMapper {

    public PartidaDTO mapPartidaToPartidaDto(Partida partida){
        PartidaDTO partidaDTO=new PartidaDTO();
        partidaDTO.setId(Integer.parseInt(partida.getId().toString()));
        partidaDTO.setDia(partida.getDia());
        partidaDTO.setPareja1(partida.getPareja1().getJugador1().getName() +  " - " + partida.getPareja1().getJugador2().getName());
        partidaDTO.setPareja2(partida.getPareja2().getJugador1().getName() +  " - " + partida.getPareja2().getJugador2().getName());
        partidaDTO.setResultado(partida.getResultado());
        if (partida.getParejaGanadora()!=null && partida.getParejaPerdedora()!=null){
            partidaDTO.setParejaGanadora(partida.getParejaGanadora().getJugador1().getName() +  " - " +partida.getParejaGanadora().getJugador2().getName());
        }
        partidaDTO.setUbicacion(partida.getUbicacion().getName());

        return partidaDTO;

    }
}
