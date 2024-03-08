package org.example.Service;

import org.example.Model.User;

import java.util.List;

public interface JugadorService {
    public List<User> findAll();
    public User findByUsername(String username);
    public boolean deleteJugador(String username);
    public User updateJugador(User jugador);
    public User insertJugador(User jugador);
}
