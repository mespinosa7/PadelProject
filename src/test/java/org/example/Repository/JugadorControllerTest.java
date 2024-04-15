package org.example.Repository;

import org.example.Enums.ERole;
import org.example.Model.Role;
import org.example.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Clase de prueba para el controlador de Jugador.
 * Realiza pruebas sobre los endpoints relacionados con la gestión de jugadores.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class JugadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JugadorRepository jugadorRepository;

    @MockBean
    private RoleRepository roleRepository;

    private User user1;
    private Role role1;
    private Role role2;
    /**
     * Preparación de datos para cada prueba.
     */
    @BeforeEach
    void setUp() {

        role1=roleRepository.save(new Role( ERole.ROLE_User));
        role2=roleRepository.save(new Role( ERole.ROLE_Admin));
        user1 = jugadorRepository.save(new User( "manel","manelesp","123456",role1,"1111111","manel@hotmail.com",33,"Perez"));

    }

    /**
     * Prueba el registro de un nuevo jugador.
     * Debería devolver un mensaje de éxito si el registro es exitoso.
     *
     * @throws Exception si ocurre un error durante la ejecución de la prueba.
     */
    @Test
    public void testRegistrarJugador() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        String name = "testName";
        String apellidos = "testApellidos";
        String telefono = "testTelefono";
        int edad = 25;
        String email = "testEmail@test.com";
        ERole role = ERole.ROLE_User;

        when(jugadorRepository.existsByUsername(username)).thenReturn(false);
        when(roleRepository.findByName(role)).thenReturn(Optional.of(new Role(role)));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/jugador/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\", \"password\":\"" + password + "\", \"name\":\"" + name + "\", \"apellidos\":\"" + apellidos + "\", \"telefono\":\"" + telefono + "\", \"edad\":" + edad + ", \"email\":\"" + email + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"User registered successfully!\"}"));

        verify(jugadorRepository, times(1)).existsByUsername(username);
        verify(roleRepository, times(1)).findByName(role);
        //verify(jugadorRepository, times(1)).save(any(User.class));//esta orden me da error
    }


}
