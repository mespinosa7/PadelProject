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
    /**
     * .
     */

    @NotNull
    private Long  id;
    private Long parejaGanadora;

    private Long parejaPerdedora;
    @NotNull
    private Long pareja1;
    @NotNull
    private Long pareja2;
    private Date dia;
    @NotNull
    private Long idUbicacion;
    private String resultado;
}