package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Esta clase representa a un set de un resultado en nuestra BD.
 */
@Entity
@Table(name = "sets")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int juegosParejaGanadora;
    private int juegosParejaPerdedora;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "resultado_id")
    private Resultado resultado;

    public Set(int juegosParejaGanadora, int juegosParejaPerdedora) {
        this.juegosParejaGanadora = juegosParejaGanadora;
        this.juegosParejaPerdedora = juegosParejaPerdedora;
    }

    @Override
    public String toString() {
        return "Set{" +
                "id=" + id +
                ", juegosParejaGanadora=" + juegosParejaGanadora +
                ", juegosParejaPerdedora=" + juegosParejaPerdedora +
                ", resultado=" + resultado +
                '}';
    }
}
