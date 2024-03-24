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
    @JoinColumn(name = "pareja_ganadora_id")
    private Pareja parejaGanadora;

    @ManyToOne()
    @JoinColumn(name = "pareja_perdedora_id")
    private Pareja parejaPerdedora;

    @OneToOne()
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    private Date dia;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "resultado_id")
    private Resultado resultado;

    public Partida(Pareja parejaGanadora, Pareja parejaPerdedora, Ubicacion ubicacion, Date dia, Resultado resultado) {
        this.parejaGanadora = parejaGanadora;
        this.parejaPerdedora = parejaPerdedora;
        this.ubicacion = ubicacion;
        this.dia = dia;
        this.resultado = resultado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partida partida = (Partida) o;
        return getId().equals(partida.getId()) && getParejaGanadora().equals(partida.getParejaGanadora()) && getParejaPerdedora().equals(partida.getParejaPerdedora()) && getUbicacion().equals(partida.getUbicacion()) && getDia().equals(partida.getDia()) && getResultado().equals(partida.getResultado());
    }

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
