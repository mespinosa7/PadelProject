package org.example.Controller;
import lombok.AllArgsConstructor;
import org.example.Model.Pareja;
import org.example.DTOs.ParejaDTO;
import org.example.Service.ParejaService;
import org.example.mapper.ParejaMapper;
import org.example.payload.request.NewParejaRequest;
import org.example.payload.response.MessageResponse;
import org.example.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para manejar las operaciones relacionadas con las Parejas.
 */
@RestController
@RequestMapping("api/pareja")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParejaController {
    private final ParejaService parejaService;
    private final ParejaMapper parejaMapper = new ParejaMapper();
    private final JwtUtils jwUtils;
    /**
     * Obtiene todas las Parejas
     *
     * @return Lista de todas las parejas.
     */
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ParejaDTO> getParejas() {
        return parejaService.findAll().stream().map(e->parejaMapper.mapParejaToParejaDto(e)).collect(Collectors.toList());
    }
    /*
    @GetMapping("/{username1}/{username2}")
    @ResponseStatus(HttpStatus.OK)
    public Pareja getParejaByUsernam1Username2(@PathVariable String username1,@PathVariable String username2) throws Exception {
        return parejaService.findByUsernames(username1,username2);

    }*/

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParejaDTO getParejaById(@PathVariable Long id) throws Exception {


        return parejaMapper.mapParejaToParejaDto(parejaService.findById(id));
    }

//    @GetMapping("/{username1}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Pareja> getParejaByUsername(@PathVariable String username1) throws Exception {
//        return parejaService.findParejasByJugador(username1);
//
//    }

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

