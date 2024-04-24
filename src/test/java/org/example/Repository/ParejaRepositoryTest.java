package org.example.Repository;

import org.example.Enums.ERole;
import org.example.Model.Pareja;
import org.example.Model.Role;
import org.example.Model.Ubicacion;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Esta clase contiene pruebas unitarias para el repositorio de Parejas (ParejasRepository).
 * Las pruebas se centran en probar el funcionamiento de los métodos del repositorio, como guardar
 * una pareja en la base de datos o buscar una Pareja por su nombre o por id.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)//NOTA: esta anotación es importante ya que sin ella los indices
//de las tablas no se resetean (al hacer un .deleteAll()) y muchos test solo darían buenos si se ejecutan independientemente..
public class ParejaRepositoryTest {
    @Autowired
    private ParejaRepository parejaRepository;
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UbicacionRepository ubicacionRepository;

    private Ubicacion ubicacion;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
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
        ubicacion = ubicacionRepository.save(new Ubicacion("la mesa","08741","Carrer Masia 75 2do 3ra"));

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
        ubicacionRepository.deleteAll();
    }

    /**
     * Con este test comprobamos que los registros se hayan insertado correctamente y los podemos recuperar. Deben de haber 2
     */
    @Test
    void findAll(){
        Integer tamanoList =2;//el numero de registros insertados es 2
        List<Pareja> lista = parejaRepository.findAll();
        /*for(int i=0;i<lista.size();i++){
            System.out.println("Numero:"+i+"-----:"+lista.get(i).toString());
        }*/
        assertEquals(tamanoList,parejaRepository.findAll().size());


    }
    /**
     * Con este test comprobamos que se puede buscar una pareja por su ID y verificamos que
     * su nombre sea el correcto con ese id.
     */
    @Test //este test funcina si se hace independiente, pero no el text global
    void findById(){//hay que hacerlo de manera individual y da bueno
        Pareja p = null;
        Optional<Pareja> pareja = parejaRepository.findById(2L);
       if(pareja.isPresent()){
            p = pareja.get();
        }

        assertTrue(p.getJugador1().getId() == 3);
        assertEquals("abraham",pareja.get().getJugador2().getName());

    }
    /**
     * Con este test comprobamos que se puede buscar una pareja por su ID en el respository
     * Y verificamos que ese id concuerde con el nombre que debería.. es igual que el método anterior, la
     * diferencia es que en este creamos nuevos jugadores.
     */
    @Test
    void buscarById(){

        //Role  role=roleRepository.save(new Role( ERole.ROLE_User));
        User  pepe = jugadorRepository.save(new User( "pepe","pepe","123456",role1,"1111111","pepe@hotmail.com",33,"Perez"));
        User  paco = jugadorRepository.save(new User( "paco","paco","123456",role1,"1111111","paco@hotmail.com",33,"Perez"));
        Ubicacion ubic = ubicacionRepository.save(new Ubicacion("la casa","08741","Carrer Masia 75 2do 3ra"));
        Pareja p = new Pareja(pepe,paco);
        parejaRepository.save(p);
        Optional<Pareja> pareja = parejaRepository.findById(p.getId());
        Pareja p2 = null;
        if(pareja.isPresent()){
            p2 = pareja.get();

        }
        System.out.println("Nombre del jugador: "+p2.getJugador2().getName());
        assertEquals("paco",p2.getJugador2().getName());

    }
    /**
     * Con este test comprobamos que se puede verificar si una pareja existe por su ID.
     */
    @Test
    void existByID(){
        assertTrue (parejaRepository.existsById(1L));
        assertTrue (parejaRepository.existsById(2L));
        assertFalse(parejaRepository.existsById(0L));
    }


    /**
     * Con este test comprobamos que se puede buscar una pareja por su nombre.
     * Este test es un prueba, no prueba un método concreto de repository
     */
    @Test
    void pruebaNombre() {
        List<Pareja> lista = parejaRepository.findAll();
        boolean nombre = false;
        if (lista.get(0).getJugador2().getName().equals("david")) {
            nombre = true;
        }
        System.out.println("Nombre del jugador en pruebaNombre: "+nombre);
        assertTrue(nombre);
    }

    /**
     * Con este test comprobamos que se puede eliminar una pareja por su ID.(solo funciona si se hace independiente)
     */
     @Test //este test funcina si se hace independiente, pero no el text global
    void deleteById(){//hay que hacerlo de manera individual y da bueno
        Pareja p = null;
        Optional<Pareja> pareja = parejaRepository.findById(2L);
        if(pareja.isPresent()){
            p = pareja.get();
        }
        assertTrue(p.getJugador1().getId() == 3);
        parejaRepository.deleteById(2L);
        assertFalse(parejaRepository.existsById(2L));

    }
    /**
     * Con este test comprobamos que se puede eliminar una pareja por su ID.
     */
    @Test
    void borrarById(){
        //primero creamos dos usuarios, ubicacion y pareja.. role no hace falta ya que coge sin problemas el ya creado, si utilizamos los creados al inicio, no se porqué
        //pero da error si se ejecuta el test de todos los metods.. para ejecutar indiviual está el test deleteById
        //Role  role=roleRepository.save(new Role( ERole.ROLE_User));
        User  pepe = jugadorRepository.save(new User( "pepe","pepe","123456",role1,"1111111","pepe@hotmail.com",33,"Perez"));
        User  paco = jugadorRepository.save(new User( "paco","paco","123456",role1,"1111111","paco@hotmail.com",33,"Perez"));
        Ubicacion ubic = ubicacionRepository.save(new Ubicacion("la casa","08741","Carrer Masia 75 2do 3ra"));
        Pareja p = new Pareja(pepe,paco);
        parejaRepository.save(p);
        //aquí el problema es que findById de JPA necesita un Optinal y hace falta hacer un cast a Pareja
        Optional<Pareja> pareja = parejaRepository.findById(p.getId());
        Pareja p2 = null;
        if(pareja.isPresent()){
            p2 = pareja.get();

        }
        //ahora ya podemos hacer las comprobaciones.. el detalle es conocer el id.. en mi caso es 12 (pero no tengo claro que siempre vaya a ser así)
        System.out.println("id de jugador: "+p2.getId());//En mi entorno el id del jugador es 12, si da error ver que id es el bueno
        assertTrue(p2.getId() == 3L);
        assertTrue(parejaRepository.existsById(3L));//comprobamos que en el repository esté la pareja con id 12
        parejaRepository.deleteById(3L);//la eliminamos
        assertFalse(parejaRepository.existsById(3L));//y comprobamos que ya no está

    }

    /**
     * Con este método probamos el método findByJugador1AndJugador2 del respository
     * Buscamos las parejas por el username de sus dos jugadores y comprobamos si existen.
     */
    @Test
    void findByJugador1AndJugador2(){
        Optional<Pareja> pareja = parejaRepository.findByJugador1AndJugador2("manelesp","david");
        assertTrue(pareja.isPresent());
        Optional<Pareja> pareja2 = parejaRepository.findByJugador1AndJugador2("alex","abraham");
        assertTrue(pareja2.isPresent());
        Optional<Pareja> pareja3 = parejaRepository.findByJugador1AndJugador2("manelesp","abraham");
        assertFalse(pareja3.isPresent());


    }


}

