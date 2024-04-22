package org.example.Controller;
import lombok.AllArgsConstructor;
import org.example.Model.Partida;
import org.example.DTOs.PartidaDTO;
import org.example.Service.PartidaService;
import org.example.mapper.PartidaMapper;
import org.example.payload.request.NewPartidaRequest;
import org.example.payload.response.MessageResponse;
import org.example.payload.response.PartidaResponse;
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
 * Controlador para manejar las operaciones relacionadas con las Partidas.
 */
@RestController
@RequestMapping("api/partida")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PartidaController {
    private final PartidaService partidaService;
    private final PartidaMapper partidaMapper = new PartidaMapper();
    private final JwtUtils jwUtils;
    /**
     * Obtiene todas las Partidas
     *
     * @return Lista de todas las partidas.
     */
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<PartidaDTO> getPartidas() {

        return partidaService.findAll().stream().map(e->partidaMapper.mapPartidaToPartidaDto(e)).collect(Collectors.toList());

    }

    /**
     * Obtiene las partidas de las que forma parte un usuario.
     * @param usernameId
     * @return Partida.
     */
    @GetMapping("jugadas/{usernameId}")
    @ResponseStatus(HttpStatus.OK)
    public PartidaResponse getPartidaByUsernameId(@PathVariable Long usernameId) throws Exception {
        return partidaService.findByUsernameId(usernameId);

    }
    /**
     * Obtiene una partida por su ID.
     *
     * @param id ID de la partida.
     * @return DTO de la partida.
     * @throws Exception Si hay un error al buscar la partida.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PartidaDTO getPartidaById(@PathVariable Long id) throws Exception {
        return partidaMapper.mapPartidaToPartidaDto(partidaService.findById(id));
    }
    /**
     * Registra una nueva partida.
     *
     * @param newPartidaRequest Objeto que contiene la información de la nueva partida.
     * @return ResponseEntity con un mensaje de éxito o error.
     * @throws Exception Si hay un error al registrar la partida.
     */
    @PostMapping("/insert")
    public ResponseEntity<?> registrarPartida(@Valid @RequestBody NewPartidaRequest newPartidaRequest) throws Exception {

        partidaService.insertPartida(newPartidaRequest);
        return ResponseEntity.ok(new MessageResponse("Partida registered successfully!"));
    }
    /**
     * Actualiza una partida existente.
     *
     * @param newPartidaRequest Objeto que contiene la información actualizada de la partida.
     * @param id                ID de la partida a actualizar.
     * @return ResponseEntity con un mensaje de éxito o error.
     * @throws Exception Si hay un error al actualizar la partida.
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updatePartida(@Valid @RequestBody NewPartidaRequest newPartidaRequest, @PathVariable Long id) throws Exception {

        partidaService.updatePartida(newPartidaRequest,id);
        return ResponseEntity.ok(new MessageResponse("Partida updated successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePartida(@PathVariable Long id) throws Exception {

        partidaService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Partida deleted successfully!"));
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

