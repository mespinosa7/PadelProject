package org.example.Repository;

import org.example.Model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de datos para la entidad de usuario (Jugador).
 */
@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    /**
     * Recupera todas los partidas.
     * @return Lista de todas las partidas.
     */
     List<Partida> findAll();
    /**
     * Busca la partida por su id.
     * @param id
     * @return El jugador encontrado.
     */
    Optional<Partida> findById(Long id);
    /**
     * Elimina una partida por su id.
     * @param id
     * @return true si se elimina correctamente, false de lo contrario.
     */
    void deleteById(Long id);
    /**
     * Busca las partidas donde participa una pareja ganadora que tenga un jugador con un nombre de usuario específico.
     *
     * @param username El nombre de usuario del jugador.
     * @return Lista de partidas donde la pareja ganadora incluye al jugador.
     */
    @Query("""
            select p from Partida p
            where p.parejaGanadora.jugador1.username = ?1 or p.parejaGanadora.jugador2.username = ?1""")
    List<Partida> findByParejaGanadora_Jugador1_UsernameOrParejaGanadora_Jugador2_Username(String username);
    /**
     * Busca las partidas donde participa una pareja perdedora que tenga un jugador con un nombre de usuario específico.
     *
     * @param username El nombre de usuario del jugador.
     * @return Lista de partidas donde la pareja perdedora incluye al jugador.
     */
    @Query("""
            select p from Partida p
            where p.parejaPerdedora.jugador1.username = ?1 or p.parejaPerdedora.jugador2.username = ?1""")
    List<Partida> findByParejaPerdedora_Jugador1_UsernameOrParejaPerdedora_Jugador2_Username(String username);


}
