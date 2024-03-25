package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Enums.ERole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
public  class User {
    /**
     * Identificador único del usuario.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false, nullable = false)
    private Long id;
    /**
     * Nombre de usuario.
     */
    private String name;
    /**
     * Nombre de usuario único utilizado para iniciar sesión.
     */
    @NotNull
    @Column(unique = true)
    private String username;
    /**
     * Contraseña del usuario.
     */
    @JsonIgnore
    @NotNull
    private String password;
    /**
     * Rol del usuario en el sistema.
     */
    @OneToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    @NotNull
    @JsonIgnore
    private String telefono;
    @NotNull
    @JsonIgnore
    private String email;
    @NotNull
    @JsonIgnore
    private int edad;
    @NotNull
    private String apellidos;
    @JsonIgnore
    private byte[] foto;

    @JsonIgnore
    @OneToMany(mappedBy = "jugador1", cascade = CascadeType.ALL)
    private List<Pareja> parejasComoJugador1 = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "jugador2", cascade = CascadeType.ALL)
    private List<Pareja> parejasComoJugador2 = new ArrayList<>();

    /**
     * Constructor de la clase User.
     * @param name Nombre del usuario.
     * @param username Nombre de usuario único.
     * @param password Contraseña del usuario.
     * @param role Rol del usuario.
     */
    public User(String name, String username, String password, Role role, String telefono, String email, int edad, String apellidos) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.telefono = telefono;
        this.email = email;
        this.edad = edad;
        this.apellidos = apellidos;
    }

    /**
     * Compara este usuario con otro objeto para determinar si son iguales.
     * @param o Objeto a comparar con este usuario.
     * @return true si el objeto es igual a este usuario, false de lo contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }
    /**
     * Devuelve una representación en cadena de este usuario.
     * @return Representación en cadena del usuario.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
