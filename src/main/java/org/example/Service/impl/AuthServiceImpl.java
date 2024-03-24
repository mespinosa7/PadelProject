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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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
    private final SetRepository setRepository;
    private final ResultadoRepository resultadoRepository;

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

    @Override
    public ResponseEntity<?> registrarJugador(SignupJugadorRequest signUpJugadorRequest) {
        if (jugadorRepository.existsByUsername(signUpJugadorRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
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

    @Override
    public void initData() {
        Role role1=roleRepository.save(new Role( ERole.ROLE_User));
        Role role2=roleRepository.save(new Role( ERole.ROLE_Admin));
        User user1 = jugadorRepository.save(new User( "manel","manelesp",encoder.encode("123456"),role1,"1111111","manel@hotmail.com",26,"espinosa"));
        User user2 = jugadorRepository.save(new User( "david","davidvalen",encoder.encode("123456"),role1,"1111111","david@hotmail.com",41,"valentin"));
        User user3 = jugadorRepository.save(new User( "alex","alextell",encoder.encode("123456"),role1,"1111111","alex@hotmail.com",33,"tellez"));
        User user4 = jugadorRepository.save(new User( "abraham","abrahamher",encoder.encode("123456"),role1,"1111111","abraham@hotmail.com",22,"herandez"));
        Ubicacion ubi1=ubicacionRepository.save(new Ubicacion("Padel Barceloneta","08020","Carrer Picasso 22"));
        Ubicacion ubi2=ubicacionRepository.save(new Ubicacion("Padel Magic Badalona", "08092","Carrer Casagemas 21"));
        Ubicacion ubi3=ubicacionRepository.save(new Ubicacion("Padel Sant Cugat Indor","08072","Carrer Capelles 32"));
        Pareja pareja1=parejaRepository.save(new Pareja(user1,user2));
        Pareja pareja2=parejaRepository.save(new Pareja(user1,user3));
        Pareja pareja3=parejaRepository.save(new Pareja(user1,user4));
        Pareja pareja4=parejaRepository.save(new Pareja(user2,user3));
        Pareja pareja5=parejaRepository.save(new Pareja(user2,user4));
        Pareja pareja6=parejaRepository.save(new Pareja(user3,user4));
        Set set1=new Set(1,6);
        Set set2=new Set(6,2);
        Set set3=new Set(6,4);
        Set set4=new Set(6,3);
        List<Set> sets= new ArrayList<>();
        sets.add(set1);
        sets.add(set2);
        sets.add(set3);
        sets.add(set4);
        Resultado resultado1=new Resultado(sets);
        Partida partida1=partidaRepository.save(new Partida(pareja1,pareja2,ubi1,new Date(2024,2,23),resultado1));
    }
}
