package org.example.Controller;
import lombok.AllArgsConstructor;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Pareja;
import org.example.Model.Ubicacion;
import org.example.Repository.UbicacionRepository;
import org.example.Service.ParejaService;
import org.example.Service.UbicacionService;
import org.example.payload.request.NewParejaRequest;
import org.example.payload.request.NewUpdateUbicacionRequest;
import org.example.payload.response.MessageResponse;
import org.example.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con las Parejas.
 */
@RestController
@RequestMapping("api/pareja")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParejaController {
    private final ParejaService parejaService;
    private final JwtUtils jwUtils;
    /**
     * Obtiene todas las Parejas
     *
     * @return Lista de todas las parejas.
     */
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Pareja> getParejas() {
        return parejaService.findAll();
    }
    /**
     * Obtiene una Pareja por el usuario del jugador1 y el usuario del jugador2.
     *
     * @param username1
     * @param username2
     * @return Pareja.
     */
    @GetMapping("/{username1}/{username2}")
    @ResponseStatus(HttpStatus.OK)
    public Pareja getParejaByUsernam1Username2(@PathVariable String username1,@PathVariable String username2) throws Exception {
        return parejaService.findByUsernames(username1,username2);

    }
    /**
     * Obtiene las parejas de las que forma parte un usuario.
     * @param username1
     * @return Pareja.
     */
    @GetMapping("/{username1}")
    @ResponseStatus(HttpStatus.OK)
    public List<Pareja> getParejaByUsername(@PathVariable String username1) throws Exception {
        return parejaService.findParejasByJugador(username1);

    }

    @PostMapping("/insert")
    public ResponseEntity<?> registrarPareja(@Valid @RequestBody NewParejaRequest newParejaRequest) throws Exception {

        parejaService.insertPareja(newParejaRequest);
        return ResponseEntity.ok(new MessageResponse("Pareja registered successfully!"));
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

