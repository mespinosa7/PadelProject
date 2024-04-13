package org.example.Service;

import org.example.Enums.ERole;
import org.example.Model.Role;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Repository.RoleRepository;
import org.example.Service.JugadorService;
import org.example.payload.request.UpdateJugadorRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 *  No funciona da error en las credenciales
 */
@SpringBootTest
public class JugadorServiceUpdateTest {

    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    private User jugador1;
    private User jugador2;
    private Role role1;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        role1 = roleRepository.save(new Role(ERole.ROLE_User));
        jugador1 = jugadorRepository.save(new User("manel", "manelesp", "123456", role1, "1111111", "manel@hotmail.com", 33, "Perez"));
        jugador2 = jugadorRepository.save(new User("david", "davidvalen", "123456", role1, "1111111", "david@hotmail.com", 33, "Valentin"));

        // Simular que la autenticación siempre es exitosa
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("davidvalen", "123456"));
    }

    @AfterEach
    void tearDown() {
        jugadorRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void updateJugador() {
        // Resto del código de la prueba
        // Configurar datos de prueba
        UpdateJugadorRequest request = new UpdateJugadorRequest();
        request = new UpdateJugadorRequest("david","Martin","99999999",45,"davidval@gmail.com","123456","123456",null,null);

        // Configurar otros campos necesarios
        String username = "davidvalen";

        // Simular que la autenticación siempre es exitosa
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(username, "123456"));

        // Llamar al método que deseas probar
        User result = jugadorService.updateJugador(request, username);

        // Realizar afirmaciones sobre el resultado esperado
        // Por ejemplo, puedes verificar si la contraseña se actualizó correctamente
         assertNotNull(result.getPassword());
         assertTrue(result.getPassword().equals("123456"));
    }


}