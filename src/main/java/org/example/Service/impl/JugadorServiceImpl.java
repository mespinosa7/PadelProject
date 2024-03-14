package org.example.Service.impl;


import lombok.AllArgsConstructor;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Service.JugadorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Implementación de la interfaz JugadorService que proporciona operaciones para la gestión de jugadores.
 */
@Service

@AllArgsConstructor
public class JugadorServiceImpl  implements JugadorService {
    private final JugadorRepository jugadorRepository;
    /**
     * Recupera todos los jugadores del repositorio.
     * @return Lista de jugadores.
     */
    @Override
    public List<User> findAll() {
        ArrayList<User> jugadores =  (ArrayList<User>)jugadorRepository.findAll();
        return jugadores;
    }
    /**
     * Busca un jugador por su nombre de usuario.
     * @param username Nombre de usuario del jugador.
     * @return El jugador encontrado.
     */
    @Override
    public User findByUsername(String username) {
        return jugadorRepository.findByUsername(username).get();
    }
    /**
     * Elimina un jugador por su nombre de usuario.
     * @param username Nombre de usuario del jugador a eliminar.
     * @return true si el jugador se elimina correctamente, false de lo contrario.
     */
    @Override
    public boolean deleteJugador(String username) {
        Boolean delete=false;
        try{
            jugadorRepository.deleteByUsername(username);
        }catch(Exception e){
            delete=false;
        }
        return delete;
    }
    /**
     * Actualiza la información de un jugador en el repositorio.
     * @param jugador Objeto User con la información actualizada del jugador.
     * @return El jugador actualizado.
     */
    @Override
    public User updateJugador(User jugador) {

        return jugadorRepository.save(jugador);
    }
    /**
     * Inserta un nuevo jugador en el repositorio.
     * @param jugador Objeto User con la información del nuevo jugador.
     * @return El nuevo jugador insertado.
     */
    @Override
    public User insertJugador(User jugador) {

        return jugadorRepository.save(jugador);
    }

}