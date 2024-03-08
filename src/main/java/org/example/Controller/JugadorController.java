package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.Model.User;
import org.example.Service.JugadorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/jugador")
//@Tag(name = "Jugador", description = "Operaciones relacionadas con Jugadores")
@AllArgsConstructor
public class JugadorController {
    private final JugadorService jugadorService;

    @GetMapping("/findAll")
    //@PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getPlayers() {
        return jugadorService.findAll();
    }

    @GetMapping("/admin/accounts/{username}")
    //@PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByUsername(@PathVariable String username) {
        return jugadorService.findByUsername(username);
    }
}
