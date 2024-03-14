package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.Model.User;
import org.example.Service.JugadorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controlador para manejar las operaciones relacionadas con los jugadores.
 */
@RestController
@RequestMapping("api/jugador")
//@Tag(name = "Jugador", description = "Operaciones relacionadas con Jugadores")
@AllArgsConstructor
public class JugadorController {
    private final JugadorService jugadorService;
    /**
     * Obtiene todos los jugadores.
     *
     * @return Lista de usuarios que representan a los jugadores.
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ROLE_Admin')")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getPlayers() {
        return jugadorService.findAll();
    }
    /**
     * Obtiene un jugador por su nombre de usuario.
     *
     * @param username Nombre de usuario del jugador.
     * @return Usuario que representa al jugador.
     */
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_User')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByUsername(@PathVariable String username) {
        return jugadorService.findByUsername(username);
    }
}
