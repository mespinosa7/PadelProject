package org.example.mapper;

import org.example.DTOs.ParejaDTO;
import org.example.Model.Pareja;
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
}
