package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.DTOs.EstadisticasJugadoresResponse;
import org.example.DTOs.EstadisticasParejasResponse;
import org.example.DTOs.JugadorDTO;
import org.example.Exceptions.NotFoundException;
import org.example.Model.User;
import org.example.Service.JugadorService;
import org.example.Service.ParejaService;
import org.example.mapper.JugadorMapper;
import org.example.payload.request.UpdateJugadorRequest;
import org.example.payload.response.MessageResponse;
import org.example.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    private final ParejaService parejaService;
    private final JugadorMapper jugadorMapper= new JugadorMapper();
    private final JwtUtils jwUtils;
    /**
     * Obtiene todos los jugadores.
     *
     * @return Lista de usuarios que representan a los jugadores.
     */
    @GetMapping("/findAll")
   // @PreAuthorize("hasRole('ROLE_Admin')")
    @ResponseStatus(HttpStatus.OK)
    public List<JugadorDTO> getPlayers() {
        return jugadorService.findAll().stream().map(e->jugadorMapper.mapJugadorToJugadorDTO(e)).collect(Collectors.toList());
    }
    /**
     * Obtiene un jugador por su nombre de usuario.
     *
     * @param username Nombre de usuario del jugador.
     * @return Usuario que representa al jugador.
     */
    @GetMapping("byUsername/{username}")
    @PreAuthorize("hasRole('ROLE_User')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByUsername(HttpServletRequest request, @PathVariable String username) throws Exception {
        String jwt = parseJwt(request);
        if(jwt != null &&  jwUtils.validateJwtToken(jwt)){
            String user = jwUtils.getUserNameFromJwtToken(jwt);
            if(!user.equalsIgnoreCase(username)){
                throw new Exception(("Usuarios diferentes"));
            }
            return jugadorService.findByUsername(username);
        }
        return null;
    }

    /**
     * Obtiene un jugador por su ID de usuario.
     *
     * @param request     La solicitud HTTP entrante.
     * @param usernameId ID del usuario.
     * @return Usuario que representa al jugador.
     * @throws Exception Si los usuarios son diferentes.
     */
    @GetMapping("/{usernameId}")
    @PreAuthorize("hasRole('ROLE_User')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByUsernameId(HttpServletRequest request, @PathVariable Long usernameId) throws Exception {
        String jwt = parseJwt(request);
        User jugador=jugadorService.findById(usernameId);
        if(jwt != null &&  jwUtils.validateJwtToken(jwt)){
            String user = jwUtils.getUserNameFromJwtToken(jwt);
            if(!user.equalsIgnoreCase(jugador.getUsername())){
                throw new Exception(("Usuarios diferentes"));
            }
            return jugador;
        }
        return null;
    }
    /**
     * Obtiene un jugador DTO por su nombre de usuario.
     *
     * @param request  La solicitud HTTP entrante.
     * @param username Nombre de usuario del jugador.
     * @return DTO del jugador.
     * @throws Exception Si los usuarios son diferentes.
     */
    @GetMapping("jugador/{username}")
    @PreAuthorize("hasRole('ROLE_User')")
    @ResponseStatus(HttpStatus.OK)
    public JugadorDTO getJugadorByUsername(HttpServletRequest request, @PathVariable String username) throws Exception {

        return jugadorMapper.mapJugadorToJugadorDTO(jugadorService.findByUsername(username));
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
     * Obtiene las estadísticas de todos los jugadores.
     *
     * @param request La solicitud HTTP entrante.
     * @return Respuesta que contiene las estadísticas de todos los jugadores.
     * @throws Exception Si ocurre un error al obtener las estadísticas.
     */
    @GetMapping("/estadisticas")
    @ResponseStatus(HttpStatus.OK)
    public EstadisticasJugadoresResponse getEstadisticasJugadores(HttpServletRequest request) throws Exception {

        return jugadorService.getEstadisticasJugadores();
    }
    /**
     * Obtiene las estadísticas de un jugador específico.
     *
     * @param request La solicitud HTTP entrante.
     * @param id      ID del jugador.
     * @return Respuesta que contiene las estadísticas del jugador.
     * @throws Exception Si ocurre un error al obtener las estadísticas.
     */
    @GetMapping("/estadisticas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadisticasParejasResponse getEstadisticasJugador(HttpServletRequest request, @PathVariable Long id) throws Exception {
        User jugador =jugadorService.findById(id);

        return jugadorService.getEstadisticasParejasJugador(parejaService.findParejasByJugador(jugador.getUsername()),jugador.getName());
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
