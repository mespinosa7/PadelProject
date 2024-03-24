package org.example.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Model.Resultado;

import javax.validation.constraints.NotBlank;
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
    private Long idParejaGanadora;
    @NotNull
    private Long idParejaPerdedora;
    @NotNull
    private Date dia;
    @NotNull
    private Long idUbicacion;
    @NotNull
    private Resultado resultado;



}