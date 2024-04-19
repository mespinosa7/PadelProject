package org.example.Repository;

import org.example.Enums.ERole;
import org.example.Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Esta clase contiene pruebas unitarias para el repositorio de Partidas (PartidasRepository).
 * Las pruebas se centran en probar el funcionamiento de los métodos del repositorio, como guardar
 * una partida en la base de datos o buscar una partida por su por id.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)//NOTA: esta anotación es importante ya que sin ella los indices
//de las tablas no se resetean y muchos test solo darían buenos si se ejecutan independientemente.. es decir, po rejemplo en el primer test
//si se crean 3 partidas, el tamaño de partidaRepository sería 3, al acabar se borrarían los registros, pero ya en el segundo test, el tamaño de
//partidaRepository también sería 3, pero los indices de los registros no serína 1,2 y 3 sino 4,5 y 6.. así con todas las tablas.
//No la he utilizado en los test anteriores(porqué no lo conocía y ahora no voy a cambiarlos ya que funcionan conrrectamente)
// pero si lo utilizaré en PartidasRepository y PartidasService y dejo constancia de la importancia de la anotación.
public class PartidaRepositoryTest {

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
    void findAll(){
        List<Partida> listaPartidas = partidaRepository.findAll();
        /*for(int i=0; i<listaPartidas.size();i++){
            System.out.println(listaPartidas.get(i).toString());
        }*/
        //el numero total de partidas es 3
        assertEquals(3,listaPartidas.size());

    }

    @Test
    void findById(){
        List<Partida> listaPartidas = partidaRepository.findAll();
        for(int i=0; i<listaPartidas.size();i++){
            System.out.println(listaPartidas.get(i).toString());
        }
        //la pareja ganadora es la número 1 en el primer y segundo registro (0 y 1)
        assertEquals(1,listaPartidas.get(0).getParejaGanadora().getId());
        //la pareja ganadora es la número 1 en el primer y segundo registro (0 y 1)
        assertEquals(1,listaPartidas.get(1).getParejaGanadora().getId());
        //la pareja ganadora es la número 2 en el último registro (2)
        assertEquals(2,listaPartidas.get(2).getParejaGanadora().getId());
        //A partir de aquí es donde realmente hacemos la comprobación del método findById
        //primero comprobamos el último registro en el que la pareja ganadora tiene id 2
        Partida p = null;
        Optional<Partida> partida = partidaRepository.findById(3L);//en el caso de la tabla el id es el 3
        if(partida.isPresent()){
            p = partida.get();
        }
        assertEquals(2,p.getParejaGanadora().getId());
        //segundo comprobamos el segundo registro en el que la pareja ganadora tiene id 1
        p = null;
        partida = partidaRepository.findById(2L);//en el caso de la tabla el id es el 3
        if(partida.isPresent()){
            p = partida.get();
        }
        assertEquals(1,p.getParejaGanadora().getId());


    }

    @Test
    void buscarParejaGanadora(){
        List<Partida> listaPartidas = partidaRepository.findByParejaGanadora_Jugador1_UsernameOrParejaGanadora_Jugador2_Username("david");
        //david se encuentra en la pareja de id 1 y han ganado dos partidas
        assertEquals(2,listaPartidas.size());
        //david es el username del segundo jugador, el del primero es manelesp, vamos a comprobarlo en la primera partida ganada por esta pareja.
        assertEquals("manelesp",listaPartidas.get(0).getPareja1().getJugador1().getUsername());
        //si comprobamos el username del segundo jugador, en el segundo registro será alex, ya que las parejas están cambiadas de lugar al introducirlas
        assertEquals("alex",listaPartidas.get(1).getPareja1().getJugador1().getUsername());
    }


    @Test
    void buscarParejaPerdedora(){
        List<Partida> listaPartidas = partidaRepository.findByParejaPerdedora_Jugador1_UsernameOrParejaPerdedora_Jugador2_Username("david");
        //david se encuentra en la pareja de id 1 y han perdido una partida
        assertEquals(1,listaPartidas.size());
        //david es el username del segundo jugador, el del primero es manelesp, vamos a comprobarlo en la primera partida ganada por esta pareja.
        assertEquals("manelesp",listaPartidas.get(0).getPareja1().getJugador1().getUsername());
    }




    @Test
    void deleteById(){
        Partida p = null;
        Optional<Partida> partida = partidaRepository.findById(3L);//en el caso de la tabla el id es el 3
        if(partida.isPresent()){
            p = partida.get();
        }
        assertEquals(2,p.getParejaGanadora().getId());
        //Ahora voy a hacer la comprobacion del método deleteById
        boolean existe = false;
        partidaRepository.deleteById(3L);
        partida = partidaRepository.findById(3L);//Volvemos a comprobar si existe el registro
        if(partida.isPresent()){
            existe = true;
        }else{
            existe = false;
        }
        assertFalse(existe);
    }




}
