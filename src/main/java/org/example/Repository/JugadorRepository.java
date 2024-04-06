package org.example.Repository;


import org.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Repositorio de datos para la entidad de usuario (Jugador).
 */
@Repository
public interface JugadorRepository extends JpaRepository<User, Long> {
    /**
     * Recupera todos los usuarios (Jugadores) almacenados en la base de datos.
     * @return Lista de todos los usuarios.
     */
    List<User> findAll();
    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario del usuario a buscar.
     * @return El usuario encontrado, o vacío si no se encuentra.
     */
    Optional<User> findByUsername(String username);
    /**
     * Elimina un usuario por su nombre de usuario.
     * @param username Nombre de usuario del usuario a eliminar.
     */
    void deleteByUsername(String username);
    /**
     * Verifica si existe un usuario con un nombre de usuario dado.
     * @param username Nombre de usuario a verificar.
     * @return Verdadero si existe un usuario con el nombre de usuario dado, falso de lo contrario.
     */
    boolean existsByUsername(String username);

    /**
     * Busca un usuario por su id de usuario.
     * @param id Nombre de usuario del usuario a buscar.
     * @return El usuario encontrado, o vacío si no se encuentra.
     */
    Optional<User> findById(Long id);
}
