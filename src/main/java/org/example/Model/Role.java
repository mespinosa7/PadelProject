package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Enums.ERole;

import javax.persistence.*;

/**
 * Clase que define los roles de los usuarios en el programa
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    /**
     * id del role
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre del role
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private ERole name;


    /**
     * Constructor de la clase
     * @param name Nombre descriptivo del role
     */
    public Role(ERole name) {
        this.id=id;
        this.name = name;
    }
}