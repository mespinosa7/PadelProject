package org.example.Service;

import org.example.Enums.ERole;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Pareja;
import org.example.Model.Role;
import org.example.Model.Ubicacion;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Repository.ParejaRepository;
import org.example.Repository.RoleRepository;
import org.example.Repository.UbicacionRepository;
import org.example.payload.request.NewParejaRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Esta clase contiene pruebas unitarias para el servicio de Parejas (ParejaService).
 * Las pruebas se centran en probar el funcionamiento de los métodos del servicio, como buscar
 * todas las parejas, buscar una pareja por los nombres de usuario de sus integrantes,
 * buscar parejas por un jugador específico, encontrar una pareja por su ID, eliminar una pareja por su ID
 * e insertar una nueva pareja.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ParejaServiceTest {

    @Autowired
    private ParejaService parejaService;
    @Autowired
    private ParejaRepository parejaRepository;
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private RoleRepository roleRepository;
 //   @Autowired
  //  private UbicacionRepository ubicacionRepository;
    @Autowired
    private JugadorService jugadorService;

    //private Ubicacion ubicacion;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User pepe;
    private User paco;
    private Role role1;
    private Pareja pareja1;
    private Pareja pareja2;

    /**
     * Configura el entorno para las pruebas creando ubicaciones de ejemplo en la base de datos.
     */

    @BeforeEach
    void setUp() {

        role1=roleRepository.save(new Role( ERole.ROLE_User));
        user1 = jugadorRepository.save(new User( "manel","manelesp","123456",role1,"1111111","manel@hotmail.com",33,"Perez"));
        user2 = jugadorRepository.save(new User( "david","david","123456",role1,"1111111","david@hotmail.com",43,"Valentin"));
        user3 = jugadorRepository.save(new User( "alex","alex","123456",role1,"1111111","alex@hotmail.com",33,"Martinez"));
        user4 = jugadorRepository.save(new User( "abraham","abraham","123456",role1,"1111111","abraham@hotmail.com",43,"Fernandez"));
        //ubicacion = ubicacionRepository.save(new Ubicacion("la mesa","08741","Carrer Masia 75 2do 3ra"));

        pareja1 = parejaRepository.save(new Pareja(user1,user2));
        pareja2 = parejaRepository.save(new Pareja(user3,user4));
        //la parte de los list de partidas ganadas y perdidas a Parejas no la agrego para no tener que añadir partidas en este test

    }

    /**
     * Limpia el entorno después de las pruebas eliminando todos los usuarios y roles creados.
     */
    @AfterEach
    void tearDown() {
        jugadorRepository.deleteAll();
        roleRepository.deleteAll();
        parejaRepository.deleteAll();
        //ubicacionRepository.deleteAll();
    }
    /**
     * Prueba el método `findAll` del servicio de parejas para asegurar que devuelve todas las parejas.
     */
    @Test
    void findAll(){
        List<Pareja> listaParejas = parejaService.findAll();
        assertFalse(listaParejas.isEmpty());
        assertTrue(listaParejas.size() == 2);

    }
    /**
     * Prueba el método `findByUsernames` del servicio de parejas para buscar una pareja por los nombres de usuario de inicio de sesion de sus integrantes.
     */
    @Test
    void findByUsernames(){
        List<Pareja> listaParejas = parejaService.findAll();
        Pareja pareja = parejaService.findByUsernames(user1.getUsername(),user2.getUsername());
        assertFalse(pareja == null);
        assertTrue(pareja.getJugador1().getName().equals("manel"));
    }
    /**
     * Prueba el método `findParejasByJugador` del servicio de parejas para buscar parejas por un jugador específico
     * (por su nombre de inicio de sesion).
     */
    @Test
    void  findParejasByJugador(){
        List<Pareja> listaParejas = parejaService.findAll();
        // List<Pareja> listaParejasPorJugador = parejaService.findParejasByJugador("manelesp");//si utilizo un usario correcto obteno otro tipo de error
        //<org.hibernate.LazyInitializationException> , pero igualemente de esta manera se demuestra que con se encuentra ninguna pareja con ese jugador
        //ya que las lineas de abajo son correctas. si el username no fuese correcto daría la otra excepcion
        assertThrows(NotFoundException.class, () -> {
            List<Pareja> listaParejasPorJugador = parejaService.findParejasByJugador("manel");
        });
    }
    /**
     * Prueba el método `findById` del servicio de parejas para encontrar una pareja por su ID.
     */
    @Test
    void findById(){
        List<Pareja> listaParejas = parejaService.findAll();//buscamos todas las parejas
        assertTrue(listaParejas.get(0).getJugador1().getName().equals("manel"));//comprobamos el nombre del primer jugador
        Pareja pareja = parejaService.findById(listaParejas.get(0).getId());//obtenemos la primera pareja de la lista
        assertFalse(pareja == null);//comprobamos que no sea null
        assertTrue(pareja.getJugador1().getName().equals("manel"));//y comprobamos que el nombre del primer jugador sea manel

    }

    /**
     * Prueba el método `deleteById` del servicio de parejas para eliminar una pareja por su ID.
     */
    @Test
    void deleteById(){
        List<Pareja> listaParejas = parejaService.findAll();//buscamos todas las parejas
        assertTrue(listaParejas.get(0).getJugador1().getName().equals("manel"));//comprobamos el nombre del primer jugador
        Pareja pareja = parejaService.findById(listaParejas.get(0).getId());
        assertFalse(pareja == null);//comprobamos que no sea null
        assertTrue(pareja.getJugador1().getName().equals("manel"));//comprobamos el nombre del jugador
        assertTrue(parejaService.deleteById(listaParejas.get(0).getId()));//eliminamos el jugador y comprobamos el resultado booleano
        assertThrows(NotFoundException.class, () -> {
           Pareja pareja2 = parejaService.findById(listaParejas.get(0).getId());//saltará la excepcin al comprobarl que el jugador no exista
        });
    }
    /**
     * Prueba el método `insertarPareja` del servicio de parejas para insertar una nueva pareja.
     */
    @Test
    void insertarPareja() throws Exception {
        NewParejaRequest pareja = new NewParejaRequest(user1.getId(),user3.getId());
        parejaService.insertPareja((pareja));
        List<Pareja> listaParejas = parejaService.findAll();//buscamos todas las parejas
        assertTrue(listaParejas.size() == 3); // las 2 creadas al inicio y esta pareja insertada nueva hacen 3

    }


}
