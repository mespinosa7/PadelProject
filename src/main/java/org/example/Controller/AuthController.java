package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.Enums.ERole;
import org.example.Model.Role;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Repository.RoleRepository;
import org.example.Service.AuthService;
import org.example.payload.request.LoginRequest;
import org.example.payload.request.SignupJugadorRequest;
import org.example.payload.response.JwtResponse;
import org.example.payload.response.MessageResponse;
import org.example.security.jwt.JwtUtils;
import org.example.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Controlador para la autenticación de usuarios y la gestión de jugadores.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    /**
     * Autentifica al usuario y genera un token JWT.
     * @param loginRequest Objeto de solicitud de inicio de sesión.
     * @return ResponseEntity con el token JWT y detalles del usuario autenticado.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return authService.authenticateUser(loginRequest);

    }
    /**
     * Registra un nuevo jugador en el sistema.
     * @param signUpJugadorRequest Objeto de solicitud de registro de jugador.
     * @return ResponseEntity con un mensaje de éxito o error.
     */
    @PostMapping("/jugador/signup")
    public ResponseEntity<?> registrarJugador(@Valid @RequestBody SignupJugadorRequest signUpJugadorRequest) {
        return authService.registrarJugador(signUpJugadorRequest);
    }


}

