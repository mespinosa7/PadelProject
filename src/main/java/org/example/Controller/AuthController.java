package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.Enums.ERole;
import org.example.Model.Role;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Repository.RoleRepository;
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
    private final AuthenticationManager authenticationManager;
    private final JugadorRepository jugadorRepository;

    //private final InitMasterDataService initMasterDataService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;


    /*@GetMapping("/init_data")
    public List<User> initData()  {
        return initMasterDataService.initData();

    }*/

    /**
     * Autentifica al usuario y genera un token JWT.
     * @param loginRequest Objeto de solicitud de inicio de sesión.
     * @return ResponseEntity con el token JWT y detalles del usuario autenticado.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponse
                .builder()
                .token(jwt)
                .username(userDetails.getUsername())
                .id(userDetails.getId())
                .roles(roles)
                .build());

    }
    /**
     * Registra un nuevo jugador en el sistema.
     * @param signUpJugadorRequest Objeto de solicitud de registro de jugador.
     * @return ResponseEntity con un mensaje de éxito o error.
     */
    @PostMapping("/jugador/signup")
    public ResponseEntity<?> registrarJugador(@Valid @RequestBody SignupJugadorRequest signUpJugadorRequest) {
        if (jugadorRepository.existsByUsername(signUpJugadorRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        User user = new User();
        user.setUsername(signUpJugadorRequest.getUsername());
        user.setName(signUpJugadorRequest.getName());
        user.setPassword( encoder.encode(signUpJugadorRequest.getPassword()));
        Role role = roleRepository.findByName(ERole.ROLE_User)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole(role);
        jugadorRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

