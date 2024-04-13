package org.example.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Clase que representa la solicitud de creacion de nueva partida.
 */
@Getter
@Setter
@NoArgsConstructor
public class NewPartidaRequest {

    @NotNull
    /**
     * Identificador único de la partida.
     */
    private Long  id;
    /**
     * Identificador de la pareja ganadora de la partida.
     */
    private Long parejaGanadora;
    /**
     * Identificador de la pareja perdedora de la partida.
     */
    private Long parejaPerdedora;
    /**
     * Identificador del primer jugador de la pareja.
     */
    @NotNull
    private Long pareja1;
    /**
     * Identificador del segundo jugador de la pareja.
     */
    @NotNull
    private Long pareja2;
    /**
     * Fecha en la que se llevó a cabo la partida.
     */
    private Date dia;
    /**
     * Identificador de la ubicación donde se realizó la partida.
     */
    @NotNull
    private Long ubicacion;
    /**
     * Resultado de la partida.
     */
    private String resultado;
}