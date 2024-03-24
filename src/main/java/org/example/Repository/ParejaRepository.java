package org.example.Repository;

import org.example.Model.Pareja;
import org.example.Model.Ubicacion;
import org.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de datos para la entidad de usuario (Jugador).
 */
@Repository
public interface ParejaRepository extends JpaRepository<Pareja, Long> {
    /**
     * Recupera todas los parejas.
     * @return Lista de todas las parejas.
     */
     List<Pareja> findAll();
    /**
     * Busca la pareja por su id.
     * @param id
     * @return El jugador encontrado.
     */
    Optional<Pareja> findById(Long id);
    /**
     * Elimina una pareja por su id.
     * @param id
     * @return true si se elimina correctamente, false de lo contrario.
     */
    void deleteById(Long id);

    /**
     * Verifica si existe una pareja con un id dado.
     * @param id
     * @return Verdadero si existe una pareja con el id.
     */
    boolean existsById(Long id);

    @Query("""
            select p from Pareja p
            where (p.jugador1.username = ?1 and p.jugador2.username = ?2) or p.jugador1.username = ?2 and p.jugador2.username = ?1""")
    Optional<Pareja> findByJugador1AndJugador2(String username1, String username2);
}
