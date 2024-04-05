package org.example.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.DTOs.PartidaDTO;
import org.example.Model.Partida;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Clase que representa la respuesta de una solicitud para ver partidas.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaResponse {
    /**
     * Lista partidas ganadas.
     */
    private List<PartidaDTO> partidasGanadas;
    /**
     * Nombre de usuario de la segunda pareja.
     */
    /**
     * Lista partidas perdidas.
     */

    private List<PartidaDTO> partidasPerdidas;

    private int totalPartidasPerdidas;
    private int totalPartidasGanadas;
    private int totalPartidas;

}