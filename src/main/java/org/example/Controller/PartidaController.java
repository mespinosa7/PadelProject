package org.example.Controller;
import lombok.AllArgsConstructor;
import org.example.Model.Partida;
import org.example.Model.PartidaOutDTO;
import org.example.Service.PartidaService;
import org.example.payload.request.NewPartidaRequest;
import org.example.payload.response.MessageResponse;
import org.example.payload.response.PartidaResponse;
import org.example.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Path;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con las Partidas.
 */
@RestController
@RequestMapping("api/partida")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PartidaController {
    private final PartidaService partidaService;
    private final JwtUtils jwUtils;
    /**
     * Obtiene todas las Partidas
     *
     * @return Lista de todas las partidas.
     */
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<PartidaOutDTO> getPartidas() {
        //Recuperamos todos los objetos y traspasamos los datos necesarios al FRONT
        List<Partida> partidasService =  partidaService.findAll();
        List<PartidaOutDTO> partidas = new ArrayList<PartidaOutDTO>();

        for(Partida partida: partidasService){
            PartidaOutDTO partidaController= new PartidaOutDTO();
            partidaController.setId(Integer.parseInt(partida.getId().toString()));
            partidaController.setDia(partida.getDia());
            partidaController.setPareja1(partida.getParejaGanadora().getJugador1().getName() +  " - " + partida.getParejaGanadora().getJugador2().getName());
            partidaController.setPareja2(partida.getParejaPerdedora().getJugador1().getName() +  " - " + partida.getParejaPerdedora().getJugador2().getName());
            partidaController.setResultado(partida.getResultado());
            partidaController.setParejaGanadora(partida.getParejaGanadora().getJugador1().getName() +  " - " +partida.getParejaGanadora().getJugador2().getName());
            partidaController.setUbicacion(partida.getUbicacion().getName());
            partidas.add(partidaController);
        }


        return partidas;
    }

    /**
     * Obtiene las partidas de las que forma parte un usuario.
     * @param username1
     * @return Partida.
     */
    @GetMapping("jugadas/{username1}")
    @ResponseStatus(HttpStatus.OK)
    public PartidaResponse getPartidaByUsername(@PathVariable String username1) throws Exception {
        return partidaService.findByUsername(username1);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PartidaOutDTO getPartidaByUsername(@PathVariable Long id) throws Exception {
        Partida partidasService =  partidaService.findById(id);

            PartidaOutDTO partidaController= new PartidaOutDTO();
            partidaController.setId(Integer.parseInt(partidasService.getId().toString()));
            partidaController.setDia(partidasService.getDia());
            partidaController.setPareja1(partidasService.getParejaGanadora().getJugador1().getName() +  " - " + partidasService.getParejaGanadora().getJugador2().getName());
            partidaController.setPareja2(partidasService.getParejaPerdedora().getJugador1().getName() +  " - " + partidasService.getParejaPerdedora().getJugador2().getName());
            partidaController.setResultado(partidasService.getResultado());
            partidaController.setParejaGanadora(partidasService.getParejaGanadora().getJugador1().getName() +  " - " +partidasService.getParejaGanadora().getJugador2().getName());
            partidaController.setUbicacion(partidasService.getUbicacion().getName());
        return partidaController;


    }

    @PostMapping("/insert")
    public ResponseEntity<?> registrarPartida(@Valid @RequestBody NewPartidaRequest newPartidaRequest) throws Exception {

        partidaService.insertPartida(newPartidaRequest);
        return ResponseEntity.ok(new MessageResponse("Partida registered successfully!"));
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updatePartida(@Valid @RequestBody NewPartidaRequest newPartidaRequest, @PathVariable Long id) throws Exception {

        partidaService.updatePartida(newPartidaRequest,id);
        return ResponseEntity.ok(new MessageResponse("Partida updated successfully!"));
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

