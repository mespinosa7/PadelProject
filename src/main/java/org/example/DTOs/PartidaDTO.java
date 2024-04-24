package org.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * Clase que representa un DTO (Data Transfer Object) para los datos de una partida concreta.
 */
public class PartidaDTO {
    Integer id;
    String parejaGanadora;
    String pareja1;
    String pareja2;
    Date dia;
    String ubicacion;
    String resultado;
}