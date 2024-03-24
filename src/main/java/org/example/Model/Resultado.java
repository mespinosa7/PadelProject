package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Esta clase representa un resultado en nuestra BD.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "resultado", cascade = CascadeType.PERSIST)
    private List<Set> sets;

    public Resultado(List<Set> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "id=" + id +
                ", sets=" + sets +
                '}';
    }
}
