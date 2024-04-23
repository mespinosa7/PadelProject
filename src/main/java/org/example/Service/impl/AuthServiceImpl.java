package org.example.Service.impl;
import lombok.AllArgsConstructor;
import org.example.Enums.ERole;
import org.example.Exceptions.NotFoundException;
import org.example.Model.*;
import org.example.Repository.*;
import org.example.Service.AuthService;
import org.example.Service.UbicacionService;
import org.example.payload.request.LoginRequest;
import org.example.payload.request.NewUpdateUbicacionRequest;
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
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de autenticación.
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JugadorRepository jugadorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UbicacionRepository ubicacionRepository;
    private final ParejaRepository parejaRepository;
    private final PartidaRepository partidaRepository;
    /**
     * Autentica un usuario con las credenciales proporcionadas.
     *
     * @param loginRequest La solicitud de inicio de sesión.
     * @return ResponseEntity con la respuesta de autenticación.
     */
    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
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
     *
     * @param signUpJugadorRequest La solicitud de registro de jugador.
     * @return ResponseEntity con la respuesta del registro.
     */
    @Override
    public ResponseEntity<?> registrarJugador(SignupJugadorRequest signUpJugadorRequest) {
        if (jugadorRepository.existsByUsername(signUpJugadorRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El Username ya existe."));
        }
        User user = new User();
        user.setUsername(signUpJugadorRequest.getUsername());
        user.setName(signUpJugadorRequest.getName());
        user.setPassword( encoder.encode(signUpJugadorRequest.getPassword()));
        user.setApellidos(signUpJugadorRequest.getApellidos());
        user.setTelefono(signUpJugadorRequest.getTelefono());
        user.setEdad(signUpJugadorRequest.getEdad());
        user.setEmail(signUpJugadorRequest.getEmail());
        Role role = roleRepository.findByName(ERole.ROLE_User)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole(role);
        jugadorRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    /**
     * Inicializa datos de prueba en la base de datos.
     */
    //script de inicio
    @Override
    public void initData() {
        Role role1=roleRepository.save(new Role( ERole.ROLE_User));
        Role role2=roleRepository.save(new Role( ERole.ROLE_Admin));
        User user1 = jugadorRepository.save(new User( "manel","manelesp",encoder.encode("123456"),role1,"1111111","manel@hotmail.com",26,"espinosa"));
        User user2 = jugadorRepository.save(new User( "david","davidvalen",encoder.encode("123456"),role1,"1111111","david@hotmail.com",41,"valentin"));
        User user3 = jugadorRepository.save(new User( "alex","alextell",encoder.encode("123456"),role1,"1111111","alex@hotmail.com",33,"tellez"));
        User user4 = jugadorRepository.save(new User( "abraham","abrahamher",encoder.encode("123456"),role1,"1111111","abraham@hotmail.com",22,"herandez"));
        User user5 = jugadorRepository.save(new User("Diego", "diegomart", encoder.encode("123456"), role1, "1111111", "diego@hotmail.com", 30, "Martin"));
        User user6 = jugadorRepository.save(new User("Laura", "lauravald", encoder.encode("123456"), role1, "1111111", "laura@hotmail.com", 28, "Valdes"));
        User user7 = jugadorRepository.save(new User("Sofia", "sofiaalva", encoder.encode("123456"), role1, "1111111", "sofia@hotmail.com", 35, "Alvarez"));
        User user8 = jugadorRepository.save(new User("Juan", "juangonz", encoder.encode("123456"), role1, "1111111", "juan@hotmail.com", 29, "Gonzalez"));
        Ubicacion ubi1=ubicacionRepository.save(new Ubicacion("Padel Barceloneta","08020","Carrer Picasso 22"));
        Ubicacion ubi2=ubicacionRepository.save(new Ubicacion("Padel Magic Badalona", "08092","Carrer Casagemas 21"));
        Ubicacion ubi3=ubicacionRepository.save(new Ubicacion("Padel Sant Cugat Indor","08072","Carrer Capelles 32"));
        Pareja pareja1=parejaRepository.save(new Pareja(user1,user2));
        Pareja pareja2=parejaRepository.save(new Pareja(user1,user3));
        Pareja pareja3=parejaRepository.save(new Pareja(user1,user4));
        Pareja pareja7=parejaRepository.save(new Pareja(user1,user5));
        Pareja pareja8=parejaRepository.save(new Pareja(user1,user6));
        Pareja pareja9=parejaRepository.save(new Pareja(user1,user7));
        Pareja pareja10=parejaRepository.save(new Pareja(user1,user8));
        Pareja pareja4=parejaRepository.save(new Pareja(user2,user3));
        Pareja pareja5=parejaRepository.save(new Pareja(user2,user4));
        Pareja pareja11=parejaRepository.save(new Pareja(user2,user5));
        Pareja pareja12=parejaRepository.save(new Pareja(user2,user6));
        Pareja pareja13=parejaRepository.save(new Pareja(user2,user7));
        Pareja pareja14=parejaRepository.save(new Pareja(user2,user8));
        Pareja pareja6=parejaRepository.save(new Pareja(user3,user4));
        Pareja pareja15=parejaRepository.save(new Pareja(user3,user5));
        Pareja pareja16=parejaRepository.save(new Pareja(user3,user6));
        Pareja pareja17=parejaRepository.save(new Pareja(user3,user7));
        Pareja pareja18=parejaRepository.save(new Pareja(user3,user8));
        Pareja pareja19=parejaRepository.save(new Pareja(user4,user5));
        Pareja pareja20=parejaRepository.save(new Pareja(user4,user6));
        Pareja pareja21=parejaRepository.save(new Pareja(user4,user7));
        Pareja pareja22=parejaRepository.save(new Pareja(user4,user8));
        Pareja pareja23=parejaRepository.save(new Pareja(user5,user6));
        Pareja pareja24=parejaRepository.save(new Pareja(user5,user7));
        Pareja pareja25=parejaRepository.save(new Pareja(user5,user8));
        Pareja pareja26=parejaRepository.save(new Pareja(user6,user7));
        Pareja pareja27=parejaRepository.save(new Pareja(user6,user8));
        Pareja pareja28=parejaRepository.save(new Pareja(user7,user8));




        Partida partida1=partidaRepository.save(new Partida(pareja1,pareja6,pareja1,pareja6, ubi1, Timestamp.valueOf("2024-04-12 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida2=partidaRepository.save(new Partida(pareja1,pareja15,pareja1,pareja15, ubi1,Timestamp.valueOf("2024-05-01 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida3=partidaRepository.save(new Partida(pareja1,pareja16,pareja16,pareja1, ubi1,Timestamp.valueOf("2024-06-02 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida4=partidaRepository.save(new Partida(pareja2,pareja5,pareja5,pareja2, ubi1,Timestamp.valueOf("2024-08-03 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida5=partidaRepository.save(new Partida(pareja2,pareja11,pareja11,pareja2, ubi1,Timestamp.valueOf("2024-10-20 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida6=partidaRepository.save(new Partida(pareja1,pareja6,pareja1,pareja6, ubi1,Timestamp.valueOf("2024-12-15 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida7=partidaRepository.save(new Partida(pareja1,pareja15,pareja1,pareja15, ubi1,Timestamp.valueOf("2024-12-13 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida8=partidaRepository.save(new Partida(pareja1,pareja16,pareja16,pareja1, ubi1,Timestamp.valueOf("2024-11-23 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida9=partidaRepository.save(new Partida(pareja2,pareja5,pareja5,pareja2, ubi1,Timestamp.valueOf("2024-04-26 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida10=partidaRepository.save(new Partida(pareja2,pareja11,pareja11,pareja2, ubi1,Timestamp.valueOf("2024-05-21 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida11=partidaRepository.save(new Partida(pareja1,pareja16,pareja1,pareja16, ubi1, Timestamp.valueOf("2024-04-12 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida12=partidaRepository.save(new Partida(pareja1,pareja25,pareja1,pareja25, ubi1,Timestamp.valueOf("2024-05-01 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida13=partidaRepository.save(new Partida(pareja1,pareja16,pareja16,pareja1, ubi1,Timestamp.valueOf("2024-06-02 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida14=partidaRepository.save(new Partida(pareja25,pareja26,pareja26,pareja25, ubi1,Timestamp.valueOf("2024-08-03 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida15=partidaRepository.save(new Partida(pareja20,pareja28,pareja20,pareja28, ubi1,Timestamp.valueOf("2024-10-20 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida16=partidaRepository.save(new Partida(pareja21,pareja25,pareja21,pareja25, ubi1,Timestamp.valueOf("2024-12-15 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida17=partidaRepository.save(new Partida(pareja11,pareja17,pareja17,pareja11, ubi1,Timestamp.valueOf("2024-12-13 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida18=partidaRepository.save(new Partida(pareja24,pareja27,pareja27,pareja24, ubi1,Timestamp.valueOf("2024-11-23 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida19=partidaRepository.save(new Partida(pareja23,pareja22,pareja23,pareja22, ubi1,Timestamp.valueOf("2024-04-26 00:00:00"),"4-6 6-2 6-3 6-4"));
        Partida partida20=partidaRepository.save(new Partida(pareja3,pareja11,pareja11,pareja3, ubi1,Timestamp.valueOf("2024-05-21 00:00:00"),"4-6 6-2 6-3 6-4"));


    }
}
