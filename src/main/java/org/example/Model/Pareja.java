package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Esta clase es la entidad pareja
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pareja {
    /**
     * Identificador único de la pareja.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false, nullable = false)
    private Long id;
    /**
     * Jugador 1.
     */
    @ManyToOne()
    @JoinColumn(name = "jugador1_id")
    @NotNull
    private User jugador1;
    /**
     * Jugador 2.
     */
    @ManyToOne()
    @JoinColumn(name = "jugador2_id")
    @NotNull
    private User jugador2;
    /**
     * pareja ganadora del partido
     */
    @JsonIgnore
    @OneToMany(mappedBy = "parejaGanadora",cascade = CascadeType.PERSIST)
    private List<Partida> partidasGanadas;
    /**
     *  pareja perdedora del partido
     */
    @JsonIgnore
    @OneToMany(mappedBy = "parejaPerdedora",cascade = CascadeType.PERSIST)
    private List<Partida> partidasPerdidas;

    /**
     * Constructor principal
     * @param jugador1
     * @param jugador2
     */
    public Pareja(User jugador1, User jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pareja pareja = (Pareja) o;
        return Objects.equals(getId(), pareja.getId()) && Objects.equals(getJugador1(), pareja.getJugador1()) && Objects.equals(getJugador2(), pareja.getJugador2());
    }

    @Override
    public String toString() {
        return "Pareja{" +
                "id=" + id +
                ", jugador1=" + jugador1 +
                ", jugador2=" + jugador2 +
                '}';
    }
}
