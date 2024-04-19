package org.example.Service.impl;


import lombok.AllArgsConstructor;
import org.example.DTOs.EstadisticasJugadoresResponse;
import org.example.DTOs.EstadisticasParejasResponse;
import org.example.Exceptions.NotFoundException;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Service.JugadorService;
import org.example.Service.ParejaService;
import org.example.mapper.JugadorMapper;
import org.example.mapper.ParejaMapper;
import org.example.payload.request.UpdateJugadorRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz JugadorService que proporciona operaciones para la gestión de jugadores.
 */
@Service

@AllArgsConstructor
public class JugadorServiceImpl  implements JugadorService {
    private final JugadorRepository jugadorRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JugadorMapper jugadorMapper;
    private final ParejaMapper parejaMapper;
    private final ParejaService parejaService;
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
        Optional<User> jugador= jugadorRepository.findByUsername(username);
        if(jugador.isEmpty()){
            throw new NotFoundException("Error: Player not found!");
        }
        return jugador.get();
    }

    /**
     * Método apra buscar un usuario por su id
     * @param ident Identificador de usuario del jugador a buscar.
     * @return
     */
    @Override
    public User findById(Long ident) {
        Optional<User> jugador= jugadorRepository.findById(ident);
        if(jugador.isEmpty()){
            throw new NotFoundException("Error: Player not found!");
        }
        return jugador.get();
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
     * @param updateJugadorRequest Objeto UpdateJugadorRequest con la información actualizada del jugador.
     * @return El jugador actualizado.
     */
    @Override
    public User updateJugador(UpdateJugadorRequest updateJugadorRequest, String username) {
        // Busca el jugador por su nombre de usuario
        User existingUser = findByUsername(username);
            // Actualiza la información del jugador
        existingUser.setName(updateJugadorRequest.getName());
        existingUser.setApellidos(updateJugadorRequest.getApellidos());
        existingUser.setTelefono(updateJugadorRequest.getTelefono());
        existingUser.setEdad(updateJugadorRequest.getEdad());
        existingUser.setEmail(updateJugadorRequest.getEmail());
        //existingUser.setRole(updateUserRequest.getRole());
        //existingUser.setFoto(updateUserRequest.getFoto());
        if(updateJugadorRequest.getPassword()!=null && !updateJugadorRequest.getPassword().isEmpty() && updateJugadorRequest.getPasswordActual()!=null && !updateJugadorRequest.getPasswordActual().isEmpty()){
            if(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, updateJugadorRequest.getPasswordActual())).isAuthenticated()){
                existingUser.setPassword(encoder.encode(updateJugadorRequest.getPassword()));
            }

        }
        return jugadorRepository.save(existingUser);
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

    /**
     * Metodo para comprobar si existe un jugador por su username
     * @param username nombre que utiliza el usuario para iniciar sesion
     * @return booleano true/false dependiendo del resultado
     */
    @Override
    public boolean existsJugador(String username) {

        return jugadorRepository.existsByUsername(username);
    }

    @Override
    public EstadisticasJugadoresResponse getEstadisticasJugadores() {
        return jugadorMapper.getEstadisticasJugadoresResponse(findAll());
    }

    @Override
    public EstadisticasParejasResponse getEstadisticasJugador(Long jugadorId) {
        User jugador=findById(jugadorId);
        return parejaMapper.getEstadisticasParejasReponsePorJugador(parejaService.findParejasByJugador(jugador.getUsername()),jugador.getName());
    }

}