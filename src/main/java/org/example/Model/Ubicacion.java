package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Esta clase representa las ubicaciones de las pistas del padel
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ubicacion {
    /**
     * Identificador único de la ubicacion.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false, nullable = false)
    private Long id;
    /**
     * Nombre de la ubicacion.
     */
    @NotNull
    private String name;
    /**
     * Codigo postal
     */
    @JsonIgnore
    @NotNull
    private String codigo_postal;
    @NotNull
    private String direccion;


    /**
     * Constructor principal , necesitamos un nombre, codigo psotal y ubicacion
     * @param name
     * @param codigo_postal
     * @param direccion
     */
    public Ubicacion(String name, String codigo_postal, String direccion) {
        this.id = id;
        this.name = name;
        this.codigo_postal = codigo_postal;
        this.direccion = direccion;
    }



    /**
     * Compara esta ubicacion con otro objeto para determinar si son iguales.
     * @param o Objeto a comparar con esta ubicacion.
     * @return true si el objeto es igual a este usuario, false de lo contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ubicacion ubicacion)) return false;
        return Objects.equals(id, ubicacion.id) && Objects.equals(name, ubicacion.name) && Objects.equals(codigo_postal, ubicacion.codigo_postal) && Objects.equals(direccion, ubicacion.direccion);
    }
    /**
     * Devuelve una representación en cadena de este usuario.
     * @return Representación en cadena del usuario.
     */
    @Override
    public String toString() {
        return "Ubicacion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codigo postal='" + codigo_postal + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

}
