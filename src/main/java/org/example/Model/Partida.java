package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Esta clase representa a un usuario en nuestra BD.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public  class Partida {
    /**
     * Identificador único de la partida.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false, nullable = false)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "pareja1")
    private Pareja pareja1;

    @ManyToOne()
    @JoinColumn(name = "pareja2")
    private Pareja pareja2;
    @ManyToOne()
    @JoinColumn(name = "pareja_ganadora_id")
    private Pareja parejaGanadora;

    @ManyToOne()
    @JoinColumn(name = "pareja_perdedora_id")
    private Pareja parejaPerdedora;

    @OneToOne()
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    private Date dia;

    private String resultado;
    /**
     * Constructor de la clase Partida con todas las propiedades.
     */
    public Partida(Pareja pareja1, Pareja pareja2,Pareja parejaGanadora, Pareja parejaPerdedora, Ubicacion ubicacion, Date dia, String resultado) {
        this.pareja1 = pareja1;
        this.pareja2 = pareja2;
        this.parejaGanadora = parejaGanadora;
        this.parejaPerdedora = parejaPerdedora;
        this.ubicacion = ubicacion;
        this.dia = dia;
        this.resultado = resultado;
    }
    /**
     * Constructor de la clase Partida sin las propiedades de parejas ganadoras y perdedoras.
     */
    public Partida(Pareja pareja1, Pareja pareja2, Ubicacion ubicacion, Date dia, String resultado) {
        this.pareja1 = pareja1;
        this.pareja2 = pareja2;
        this.ubicacion = ubicacion;
        this.dia = dia;
        this.resultado = resultado;
    }
    /**
     * Método equals para comparar dos objetos Partida.
     *
     * @param o Objeto a comparar con esta partida.
     * @return true si los objetos son iguales, false de lo contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partida partida = (Partida) o;
        return getId().equals(partida.getId()) && getParejaGanadora().equals(partida.getParejaGanadora()) && getParejaPerdedora().equals(partida.getParejaPerdedora()) && getUbicacion().equals(partida.getUbicacion()) && getDia().equals(partida.getDia()) && getResultado().equals(partida.getResultado());
    }
    /**
     * Método toString que devuelve una representación en cadena de esta partida.
     *
     * @return Una cadena que representa esta partida.
     */
    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", parejaGanadora=" + parejaGanadora +
                ", parejaPerdedora=" + parejaPerdedora +
                ", ubicacion=" + ubicacion +
                ", dia=" + dia +
                ", resultado=" + resultado +
                '}';
    }
}
