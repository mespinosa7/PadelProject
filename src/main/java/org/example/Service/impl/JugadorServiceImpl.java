package org.example.Service.impl;


import lombok.AllArgsConstructor;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Service.JugadorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

@AllArgsConstructor
public class JugadorServiceImpl  implements JugadorService {
    private final JugadorRepository jugadorRepository;

    @Override
    public List<User> findAll() {
        ArrayList<User> jugadores =  (ArrayList<User>)jugadorRepository.findAll();
        return jugadores;
    }

    @Override
    public User findByUsername(String username) {
        return jugadorRepository.findByUsername(username).get();
    }

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

    @Override
    public User updateJugador(User jugador) {
        return jugadorRepository.save(jugador);
    }

    @Override
    public User insertJugador(User jugador) {
        return jugadorRepository.save(jugador);
    }

}