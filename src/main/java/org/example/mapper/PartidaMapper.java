package org.example.mapper;

import org.example.DTOs.ParejaDTO;
import org.example.DTOs.PartidaDTO;
import org.example.Model.Pareja;
import org.example.Model.Partida;
/**
 * Clase que se encarga de mapear un objeto de tipo Partida a un objeto de tipo PartidaDTO.
 */
public class PartidaMapper {
    /**
     * Convierte un objeto Partida en un objeto PartidaDTO.
     *
     * @param partida El objeto Partida a ser mapeado.
     * @return El objeto PartidaDTO mapeado.
     */
    public PartidaDTO mapPartidaToPartidaDto(Partida partida){
        PartidaDTO partidaDTO=new PartidaDTO();
        partidaDTO.setId(Integer.parseInt(partida.getId().toString()));
        partidaDTO.setDia(partida.getDia());
        partidaDTO.setPareja1(partida.getPareja1().getJugador1().getName() +  " - " + partida.getPareja1().getJugador2().getName());
        partidaDTO.setPareja2(partida.getPareja2().getJugador1().getName() +  " - " + partida.getPareja2().getJugador2().getName());
        partidaDTO.setResultado(partida.getResultado()!=null?partida.getResultado():null);
        if (partida.getParejaGanadora()!=null && partida.getParejaPerdedora()!=null){
            partidaDTO.setParejaGanadora(partida.getParejaGanadora().getJugador1().getName() +  " - " +partida.getParejaGanadora().getJugador2().getName());
        }
        partidaDTO.setUbicacion(partida.getUbicacion().getName());

        return partidaDTO;

    }
}
