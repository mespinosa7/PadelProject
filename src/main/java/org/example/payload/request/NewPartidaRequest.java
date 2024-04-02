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
    private Long idPareja1;
    @NotNull
    private Long idPareja2;
    private Long idParejaGanadora;
    private Long idParejaPerdedora;
    @NotNull
    private Date dia;
    @NotNull
    private Long idUbicacion;
    private String resultado;

}