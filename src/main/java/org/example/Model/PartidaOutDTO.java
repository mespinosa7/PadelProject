package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PartidaOutDTO {
    Integer id;
    String parejaGanadora;
    String pareja1;
    String pareja2;
    Date dia;
    String ubicacion;
    String resultado;
}