package org.example.Service;

import org.example.Enums.ERole;
import org.example.Exceptions.NotFoundException;
import org.example.Model.*;
import org.example.Repository.*;
import org.example.payload.request.NewPartidaRequest;
import org.example.payload.response.PartidaResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Esta clase contiene pruebas unitarias para el repositorio de Partidas (PartidasService).
 * Las pruebas se centran en probar el funcionamiento de los métodos del repositorio, como guardar
 * una partida en la base de datos o buscar una partida por su por id ...etc
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)//NOTA: esta anotación es importante ya que sin ella los indices
//de las tablas no se resetean (al hacer un .deleteAll()) y muchos test solo darían buenos si se ejecutan independientemente.. es decir,
// por ejemplo en el primer test
//si se crean 3 partidas, el tamaño de partidaRepository sería 3, al acabar se borrarían los registros, pero ya en el segundo test, el tamaño de
//partidaRepository también sería 3, pero los ids de los registros no serían 1,2 y 3 sino 4,5 y 6.. así con todas las tablas.
//No la he utilizado en los test anteriores(porqué no lo conocía y ahora no voy a cambiarlos ya que funcionan conrretamente)
// pero si lo utilizaré en PartidasRepository y PartidasService y dejo constancia de la importancia de la anotación.
public class PartidaServiceTest {
    @Autowired
    PartidaService partidaService;
    @Autowired
    PartidaRepository partidaRepository;
    @Autowired
    private ParejaRepository parejaRepository;
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UbicacionRepository ubicacionRepository;
    private Partida partida1;
    private Partida partida2;
    private Ubicacion ubicacion;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private Role role1;
    private Pareja pareja1;
    private Pareja pareja2;

    /**
     * Configura el entorno para las partidas creando partidas de ejemplo en la base de datos.
     */

    @BeforeEach
    void setUp() {

        role1=roleRepository.save(new Role( ERole.ROLE_User));
        user1 = jugadorRepository.save(new User( "manel","manelesp","123456",role1,"1111111","manel@hotmail.com",33,"Perez"));
        user2 = jugadorRepository.save(new User( "david","david","123456",role1,"1111111","david@hotmail.com",43,"Valentin"));
        user3 = jugadorRepository.save(new User( "alex","alex","123456",role1,"1111111","alex@hotmail.com",33,"Martinez"));
        user4 = jugadorRepository.save(new User( "abraham","abraham","123456",role1,"1111111","abraham@hotmail.com",43,"Fernandez"));
        ubicacion = ubicacionRepository.save(new Ubicacion("la mesa","08741","Carrer Masia 75 2do 3ra"));

        pareja1 = parejaRepository.save(new Pareja(user1,user2));
        pareja2 = parejaRepository.save(new Pareja(user3,user4));
        long ms = 900000000;
        long ms2 = 900999999;
        long ms3 = 900999000;
        Date fechaPartida1 = new Date(ms);
        Date fechaPartida2 = new Date(ms2);
        Date fechaPartida3 = new Date(ms3);
        partida1 = partidaRepository.save(new Partida(pareja1,pareja2,pareja1,pareja2,ubicacion,fechaPartida1,"6-2 6-3"));
        partida2 = partidaRepository.save(new Partida(pareja2,pareja1,pareja1,pareja2,ubicacion,fechaPartida2,"3-6 6-3 6-4"));
        partida2 = partidaRepository.save(new Partida(pareja1,pareja2,pareja2,pareja1,ubicacion,fechaPartida3,"3-6 6-5 4-6"));

    }

    /**
     * Limpia el entorno después de las pruebas eliminando todos los usuarios y roles creados.
     */
    @AfterEach
    void tearDown() {
        partidaRepository.deleteAll();
        parejaRepository.deleteAll();
        ubicacionRepository.deleteAll();
        jugadorRepository.deleteAll();
        roleRepository.deleteAll();
    }


    @Test
    void  findall(){
        List<Partida> listaPartidas = partidaService.findAll();
        /*for(int i=0; i<listaPartidas.size();i++){
            System.out.println(listaPartidas.get(i).toString());
        }*/
        //el numero total de partidas es 3
        assertEquals(3,listaPartidas.size());
        //la pareja ganadora es la número 1 en el primer y segundo registro (0 y 1)
        assertEquals(1,listaPartidas.get(0).getParejaGanadora().getId());
        //la pareja ganadora es la número 1 en el primer y segundo registro (0 y 1)
        assertEquals(1,listaPartidas.get(1).getParejaGanadora().getId());
        //la pareja ganadora es la número 2 en el último registro (2)
        assertEquals(2,listaPartidas.get(2).getParejaGanadora().getId());

    }

    @Test
    void findByUsernameId(){

        PartidaResponse partidaResponse = partidaService.findByUsernameId(2L);
        //el jugador david con id 2 que está en la pareja 1 ha ganado 2 partidas y perdido 1, en total 3 jugadas
        assertEquals(2,partidaResponse.getPartidasGanadas().size());
        //el jugador david con id 2 que está en la pareja 1 ha perdido 1 partida
        assertEquals(1,partidaResponse.getTotalPartidasPerdidas());
        //el jugador david con id 2 que está en la pareja 1 ha jugado 3 partidas
        assertEquals(3,partidaResponse.getTotalPartidas());

    }

    @Test
    void findById(){
        //en el caso de que el id de la partida sea el 3
        Partida partida = partidaService.findById(3L);
        //y la pareja ganadora es la numero 2
        assertEquals(2,partida.getParejaGanadora().getId());
        //en el caso de que el id de la partida sea el 1
        partida = partidaService.findById(1L);
        //y la pareja ganadora es la numero 1
        assertEquals(1,partida.getParejaGanadora().getId());

    }


    @Test
    void insertPartida() throws Exception {
        long ms = 900587459;
        Date fechaPartida1 = new Date(ms);
        NewPartidaRequest newPartidaRequest = new NewPartidaRequest(4L,2L,1L,1L,2L,fechaPartida1,1L,"6-4 6-4 6-4");
        Partida partida = partidaService.insertPartida(newPartidaRequest);
        long ms2 = 900587400;
        Date fechaPartida2 = new Date(ms2);
        NewPartidaRequest newPartidaRequest2 = new NewPartidaRequest(5L,2L,1L,1L,2L,fechaPartida1,1L,"6-4 6-1 6-0");
        Partida partida2 = partidaService.insertPartida(newPartidaRequest2);
        long ms3 = 900587411;
        Date fechaPartida3 = new Date(ms3);
        NewPartidaRequest newPartidaRequest3 = new NewPartidaRequest(6L,1L,2L,1L,2L,fechaPartida1,1L,"4-6 4-6 4-6");
        Partida partida3 = partidaService.insertPartida(newPartidaRequest3);
        //la pareja ganadora de esta partida es la que tiene id 1
        assertEquals(1L,partida3.getParejaGanadora().getId());
        List<Partida> listaPartidas = partidaService.findAll();
        for(int i=0;i<listaPartidas.size();i++){
            System.out.println(listaPartidas.get(i).toString());
        }
        //el id de la partida es el 5 pero el arrayList comienza por 0 así que es el 4 el que tenemos que coger
        assertEquals("6-4 6-1 6-0",partidaService.findAll().get(4).getResultado());
        //el id de la partida es el 6 pero el arrayList comienza por 0 así que es el 5 el que tenemos que coger
        assertEquals("4-6 4-6 4-6",partidaService.findAll().get(5).getResultado());


    }



    @Test
    void updatePartida() throws Exception {
        long ms = 900587459;
        Date fechaPartida1 = new Date(ms);
        NewPartidaRequest newPartidaRequest = new NewPartidaRequest(1L,1L,2L,1L,2L,fechaPartida1,1L,"6-0 6-0 6-0");
        Partida partida = partidaService.updatePartida(newPartidaRequest,1L);
        //el id de la partida es el 1 pero el arrayList comienza por 0 así que es el 0 el que tenemos que coger
        assertEquals("6-0 6-0 6-0",partidaService.findAll().get(0).getResultado());
        //ahora comprobamos el resultado del objeto partida (que evidentemente es el mismo)
        assertEquals("6-0 6-0 6-0",partida.getResultado());


    }


    @Test
    void deleteById(){
        //en el caso de que el id de la partida sea el 3
        Partida partida = partidaService.findById(3L);
        //y la pareja ganadora es la numero 2
        assertEquals(2,partida.getParejaGanadora().getId());
        //Ahora voy a hacer la comprobacion del método deleteById
        boolean existe = false;
        partidaService.deleteById(3L);
       // partida = partidaService.findById(3L);//Volvemos a comprobar si existe el registro
        assertThrows(NotFoundException.class, () -> {
            Partida partida2 = partidaService.findById(3L);
        });

    }





}
