package org.example.Service;

import org.example.DTOs.EstadisticasJugadoresResponse;
import org.example.DTOs.EstadisticasParejasResponse;
import org.example.Model.Pareja;
import org.example.Model.User;
import org.example.payload.request.UpdateJugadorRequest;

import javax.validation.Valid;
import java.util.List;
/**
 * Interfaz que define los métodos para la gestión de jugadores.
 */
public interface JugadorService {
    /**
     * Recupera todos los jugadores.
     * @return Lista de todos los jugadores.
     */
    public List<User> findAll();
    /**
     * Busca un jugador por su nombre de usuario.
     * @param username Nombre de usuario del jugador a buscar.
     * @return El jugador encontrado.
     */
    public User findByUsername(String username);
    /**
     * Busca un jugador por su Id de  usuario.
     * @param id Identificador de usuario del jugador a buscar.
     * @return El jugador encontrado.
     */
    public User findById(Long id);

    /**
     * Elimina un jugador por su nombre de usuario.
     * @param username Nombre de usuario del jugador a eliminar.
     * @return true si se elimina correctamente, false de lo contrario.
     */
    public boolean deleteJugador(String username);
    /**
     * Actualiza la información de un jugador.
     * @param jugador Objeto User con la información actualizada del jugador.
     * @return El jugador actualizado.
     */
    public User updateJugador(@Valid UpdateJugadorRequest jugador, String username);
    /**
     * Inserta un nuevo jugador.
     * @param jugador Objeto User con la información del nuevo jugador.
     * @return El nuevo jugador insertado.
     */
    public User insertJugador(User jugador);

    /**
     * Comrpueba si existe un jugador
     * @param username nombre que utiliza el usuario para iniciar sesion
     * @return verdadero o false dependiendo del resultado
     */
    boolean existsJugador(String username);

    EstadisticasJugadoresResponse getEstadisticasJugadores();

    EstadisticasParejasResponse getEstadisticasParejasJugador(List<Pareja> parejasJugador, String name);
}
