package org.example.Controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.Exceptions.NotFoundException;
import org.example.Model.User;
import org.example.Service.JugadorService;
import org.example.payload.request.UpdateJugadorRequest;
import org.example.payload.response.MessageResponse;
import org.example.payload.response.UserResponse;
import org.example.security.jwt.AuthTokenFilter;
import org.example.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
/**
 * Controlador para manejar las operaciones relacionadas con los jugadores.
 */
@RestController
@RequestMapping("api/jugador")
//@Tag(name = "Jugador", description = "Operaciones relacionadas con Jugadores")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class JugadorController {
    private final JugadorService jugadorService;
    private final JwtUtils jwUtils;
    /**
     * Obtiene todos los jugadores.
     *
     * @return Lista de usuarios que representan a los jugadores.
     */
    @GetMapping("/findAll")
   // @PreAuthorize("hasRole('ROLE_Admin')")
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
    public UserResponse getUserByUsername(HttpServletRequest request, @PathVariable String username) throws Exception {
        String jwt = parseJwt(request);
        if(jwt != null &&  jwUtils.validateJwtToken(jwt)){
            String user = jwUtils.getUserNameFromJwtToken(jwt);
            if(!user.equalsIgnoreCase(username)){
                throw new Exception(("Usuarios diferentes"));
            }
            return jugadorService.findUserByUsername(username);
        }
        return null;
    }

    @GetMapping("jugador/{username}")
    @PreAuthorize("hasRole('ROLE_User')")
    @ResponseStatus(HttpStatus.OK)
    public User getJugadorByUsername(HttpServletRequest request, @PathVariable String username) throws Exception {

        return jugadorService.findByUsername(username);
    }

    /**
     * Actualiza la información de un jugador existente.
     *
     * @param username             Nombre de usuario del jugador a actualizar.
     * @param updateUserRequest   Objeto que contiene la información actualizada del jugador.
     * @return ResponseEntity con un mensaje de éxito o error.
     */
    @PutMapping("/update/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateJugador(HttpServletRequest request,@PathVariable String username, @Valid @RequestBody UpdateJugadorRequest updateUserRequest) throws Exception {
        String jwt = parseJwt(request);
        if(jwt != null &&  jwUtils.validateJwtToken(jwt)) {
            String user = jwUtils.getUserNameFromJwtToken(jwt);
            if (!user.equalsIgnoreCase(username)) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Usuarios diferentes!"));
            }
            try {
                jugadorService.updateJugador(updateUserRequest, username);
            } catch (NotFoundException ex) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(ex.getMessage()));
            }

        }

        return ResponseEntity.ok(new MessageResponse("Player updated successfully!"));


    }

    /**
     * Este método analiza el encabezado de autorización de la solicitud para extraer el token JWT.
     *
     * @param request La solicitud HTTP entrante.
     * @return El token JWT si está presente en el encabezado de autorización, o nulo si no se encuentra.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }



}
