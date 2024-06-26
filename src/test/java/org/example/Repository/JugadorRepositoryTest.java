package org.example.Repository;



import org.example.Enums.ERole;
import org.example.Model.Role;
import org.example.Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Esta clase contiene pruebas unitarias para el repositorio de jugadores (JugadorRepository).
 * Las pruebas se centran en probar el funcionamiento de los métodos del repositorio, como guardar
 * un jugador en la base de datos y buscar un jugador por su nombre de usuario o por id.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)//NOTA: esta anotación es importante ya que sin ella los indices
//de las tablas no se resetean (al hacer un .deleteAll()) y muchos test solo darían buenos si se ejecutan independientemente..
class JugadorRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JugadorRepository jugadorRepository;

    private User user1;
    private User user2;
    private User user3;
    private Role role1;
    private Role role2;
    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private ParejaRepository parejaRepository;

    /**
     * Configura el entorno para las pruebas creando roles y usuarios de ejemplo en la base de datos.
     */
    @BeforeEach
    void setUp() {

        role1=roleRepository.save(new Role( ERole.ROLE_User));
        role2=roleRepository.save(new Role( ERole.ROLE_Admin));
        user1 = jugadorRepository.save(new User( "manel","manelesp","123456",role1,"1111111","manel@hotmail.com",33,"Perez"));
        user2 = jugadorRepository.save(new User( "ignasi","ignasi","123456",role1,"1111111","ignssi@hotmail.com",33,"Val"));
        user3 = jugadorRepository.save(new User( "david","david","123456",role1,"1111111","david@hotmail.com",33,"Rodriguez"));
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
     * Prueba si se puede encontrar la lista de jugadores en el repository
     * y verifica que el tamaño sea correcto
     */
    @Test
    void findAll(){
        List<User> listaJugadores = jugadorRepository.findAll() ;
        assertEquals(3,listaJugadores.size());

    }
    /**
     * Prueba si se puede encontrar un jugador por su id de usuario en el repositorio.
     * Además, se verifica si el id de ese usuario es correcto(2).
     */

    @Test
    void findById(){
        Optional<User> jugador = jugadorRepository.findById(2L);
        User jug = null;
        if(jugador.isPresent()){
            jug = jugador.get();
        }
        assertEquals(2,jug.getId());
    }
    /**
     * Prueba si se puede encontrar un jugador por su nombre de usuario en el repositorio.
     * Se intenta recuperar un jugador por su nombre de usuario ("manelesp") y se verifica
     * si el jugador recuperado es igual al jugador creado en el método setUp.
     * Además, se verifica si no se puede encontrar ningún jugador con un nombre de usuario inexistente ("Boing").
     */
    @Test
    void findByUsername() {
        assertEquals(user1,jugadorRepository.findByUsername("manelesp").get());
        assertTrue( jugadorRepository.findByUsername("Boing").isEmpty());

    }

    /**
     * Prueba si la edición de un usuario del repositorio a traves de setter es correcta
     * Primero comprobamos su telelefno, luego lo editamos y comprobamos si la edicion ha
     * tenido exito
     */
    @Test
    void updateJugador() {
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
        assertEquals(user1,jugadorRepository.findByUsername("manelesp").get());
        jugadorRepository.delete(jugadorRepository.findByUsername("manelesp").get());
        assertTrue( jugadorRepository.findByUsername("manelesp").isEmpty());
    }

    /**
     * Prueba si existen jugadores comprobando su username
     */
    @Test
    void existByUsername(){
        assertTrue( jugadorRepository.existsByUsername("manelesp"));
        assertTrue( jugadorRepository.existsByUsername("ignasi"));
        assertTrue( jugadorRepository.existsByUsername("david"));
        assertFalse( jugadorRepository.existsByUsername("juan"));

    }







}

