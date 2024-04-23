package org.example.Controller;
import lombok.AllArgsConstructor;
import org.example.Enums.ERole;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Role;
import org.example.Model.Ubicacion;
import org.example.Model.User;
import org.example.Repository.UbicacionRepository;
import org.example.Service.UbicacionService;
import org.example.payload.request.SignupJugadorRequest;
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
 * Controlador para manejar las operaciones relacionadas con las Ubicaciones.
 */
@RestController
@RequestMapping("api/ubicacion")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UbicacionController {
    private final UbicacionService ubicacionService;
    private final UbicacionRepository ubicacionRepository;
    private final JwtUtils jwUtils;
    /**
     * Obtiene todas las Ubicaciones
     *
     * @return Lista de ubicaciones que representan a los pistas.
     */
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Ubicacion> getUbicaciones() {
        return ubicacionService.findAll();
    }
    /**
     * Obtiene una ubicacion por su id.
     *
     * @param id
     * @return Ubicacion.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ubicacion getUbicacionByName(@PathVariable Long id) throws Exception {
        return ubicacionService.findById(id);

    }

    /**
     * Actualiza la información de una ubicacion existente.
     *
     * @param id
     * @param updateUbicacionRequest   Objeto que contiene la información actualizada de la ubicacion.
     * @return ResponseEntity con un mensaje de éxito o error.
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateUbicacion(HttpServletRequest request,@PathVariable Long id, @Valid @RequestBody NewUpdateUbicacionRequest updateUbicacionRequest) throws Exception {
        String jwt = parseJwt(request);
        if(jwt != null &&  jwUtils.validateJwtToken(jwt)) {
            try {
                ubicacionService.updateUbicacion(updateUbicacionRequest, id);
            } catch (NotFoundException ex) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(ex.getMessage()));
            }

        }

        return ResponseEntity.ok(new MessageResponse("Ubicacion updated successfully!"));


    }

    /**
     * Borra una ubicacion utilizando su id para buscarla en el Repository
     * @param request La solicitud HTTP entrante.
     * @param id
     * @return ResponseEntity con un mensaje de éxito.
     * @throws Exception
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateUbicacion(HttpServletRequest request,@PathVariable Long id) throws Exception {

        return ubicacionService.deleteById(id)?ResponseEntity.ok(new MessageResponse("Ubicación borrada correctamente!")):
                ResponseEntity.ok(new MessageResponse("Ubicación no se ha podido borrar al pertenecer a una partida"));




    }

    /**
     * Inserta una nuevoa ubicacion
     * @param nuevaUbicacion
     * @return ResponseEntity con un mensaje de éxito.
     */

    @PostMapping("/insert")
    public ResponseEntity<?> registrarUbicacion(@Valid @RequestBody NewUpdateUbicacionRequest nuevaUbicacion) {
        if (ubicacionRepository.existsByName(nuevaUbicacion.getName())) {

            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La ubicacion is already taken!"));
        }
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setName(nuevaUbicacion.getName());
        ubicacion.setCodigo_postal(nuevaUbicacion.getCodigo_postal());
        ubicacion.setDireccion(nuevaUbicacion.getDireccion());
        ubicacionRepository.save(ubicacion);
        return ResponseEntity.ok(new MessageResponse("Ubicacion registered successfully!"));
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

