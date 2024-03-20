package org.example.Repository;



import org.example.Enums.ERole;
import org.example.Model.Role;
import org.example.Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Esta clase contiene pruebas unitarias para el repositorio de jugadores (JugadorRepository).
 * Las pruebas se centran en probar el funcionamiento de los métodos del repositorio, como guardar
 * un jugador en la base de datos y buscar un jugador por su nombre de usuario.
 */
@SpringBootTest
class JugadorRepositoryTest {


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JugadorRepository jugadorRepository;

    private User user1;
    private Role role1;
    private Role role2;
    /**
     * Configura el entorno para las pruebas creando roles y usuarios de ejemplo en la base de datos.
     */
    @BeforeEach
    void setUp() {

        role1=roleRepository.save(new Role( ERole.ROLE_User));
        role2=roleRepository.save(new Role( ERole.ROLE_Admin));
        user1 = jugadorRepository.save(new User( "manel","manelesp","123456",role1,"1111111","manel@hotmail.com",33,"Perez"));

    }
    /**
     * Limpia el entorno después de las pruebas eliminando todos los usuarios y roles creados.
     */
    @AfterEach
    void tearDown() {
        jugadorRepository.deleteAll();
        roleRepository.deleteAll();
    }
    /**
     * Prueba si se puede guardar un nuevo jugador correctamente en el repositorio.
     * Se crea un nuevo usuario y se guarda en el repositorio de jugadores.
     * Luego se comprueba si el usuario se puede recuperar correctamente del repositorio
     * utilizando el método findByUsername.
     */
    @Test
    void save_jugador_successful() {

        User user2 = jugadorRepository.save(new User( "ignasi","manelesp1","123456",role1,"1111111","manel@hotmail.com",33,"Perez"));
        assertEquals(user2,jugadorRepository.findByUsername("manelesp1").get());

    }
    /**
     * Prueba si se puede encontrar un jugador por su nombre de usuario en el repositorio.
     * Se intenta recuperar un jugador por su nombre de usuario ("manelesp") y se verifica
     * si el jugador recuperado es igual al jugador creado en el método setUp.
     * Además, se verifica si no se puede encontrar ningún jugador con un nombre de usuario inexistente ("Boing").
     */
    @Test
    void findByUsername() {
        User jugador = jugadorRepository.findByUsername("manelesp").get();
        assertEquals(user1,jugador);
        assertTrue( jugadorRepository.findByUsername("Boing").isEmpty());

    }

    /**
     * Prueba si la edición de un usuario del repositorio a traves de setter es correcta
     * Primero comprobamos su telelefno, luego lo editamos y comprobamos si la edicion ha
     * tenido exito
     */
    @Test
    void updateJugador() {
        //User jugador = jugadorRepository.findByUsername("manelesp").get();
        assertEquals("1111111", user1.getTelefono());
        user1.setTelefono("222222222");
        assertEquals("222222222", user1.getTelefono());
        //assertTrue( jugadorRepository.findByUsername("Boing").isEmpty());
    }



    /**
     * Prueba si la edición de un usuario del repositorio a traves de setter es correcta
     */

    @Test
    void deleteJugador() {
        User jugador = jugadorRepository.findByUsername("manelesp").get();
        assertEquals(user1,jugador);
        jugadorRepository.delete(jugadorRepository.findByUsername("manelesp").get());
        assertTrue( jugadorRepository.findByUsername("manelesp").isEmpty());
    }






}

