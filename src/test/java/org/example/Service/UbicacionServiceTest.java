package org.example.Service;


import org.example.Model.Ubicacion;
import org.example.Repository.UbicacionRepository;
import org.example.payload.request.NewUpdateUbicacionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Esta clase contiene pruebas unitarias para UbicacionService.
 * Las pruebas incluyen la verificación de algunos métodos de CRUD y operaciones de búsqueda.
 */

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UbicacionServiceTest {

    @Autowired
    private UbicacionService ubicacionService;
    @Autowired
    private UbicacionRepository ubicacionRepository;

    private Ubicacion ubicacion;
    private Ubicacion ubicacion2;


    /**
     * Configuración inicial para cada prueba.
     * Se crea y guarda un par de ubicaciones en la base de datos antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        //ubicacionRepository.deleteAll();
        ubicacion = ubicacionRepository.save(new Ubicacion("la mesa","08741","Carrer Masia 75 2do 3ra"));
        ubicacion2 = ubicacionRepository.save(new Ubicacion("la silla","08722","Carrer Perejil 15 s/n"));
    }

    /**
     * Limpieza después de cada prueba.
     * Se eliminan todas las ubicaciones de la base de datos.
     */
    @AfterEach
    void tearDown() {
        ubicacionRepository.deleteAll();
    }

    /**
     * Prueba la operación de búsqueda del método findByAll.
     * Verifica que la ubicación 2 se encuentre en la lista recuperada.
     * La manera de probar el método es guardar todas las ubicaciones y buscar luego una cocreta
     * Se podría simplemente demostrar que hay mas de una ubicacion guardada.
     */
    @Test
    void findByAll(){
        List<Ubicacion> lista = new ArrayList<Ubicacion>();
        boolean resultado = false;
        lista = ubicacionService.findAll();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getId() == ubicacion2.getId()){//buscamos una ubicacion, en este caso la numero 2
                resultado = true;
            }
        }
        assertTrue(resultado,"No se ha podido encontrar el objeto");

    }
    /**
     * Prueba la operación de búsqueda de una ubicación por su ID.
     * Compara la ubicación recuperada con la ubicación original.
     */
    @Test
    void findById(){
        System.out.println("ubicacion.getId(): "+ubicacion.getId());
        assertEquals(ubicacion,ubicacionService.findById(ubicacion.getId()));

    }

    /**
     * Prueba la operación de eliminación de una ubicación.
     * Verifica que la ubicación eliminada ya no esté presente en la base de datos.
     */
    @Test
    void deleteUbicacion(){
        Ubicacion ubicacion = ubicacionRepository.save(new Ubicacion("la pala2","08751","Carrer lechuga 15 s/n"));
        System.out.println(ubicacion.toString());//con esto vemos que el id es 6
        assertEquals(ubicacion,ubicacionService.findById(ubicacion.getId()));
        boolean resultado = ubicacionService.deleteById(ubicacion.getId());
        assertFalse(resultado, "El objeto no debería estar.. algún error ha ocurrido");

    }

    /**
     * Prueba la operación de actualización de una ubicación.
     * Se actualiza la ubicación 2 y se verifica que los cambios se hayan aplicado correctamente.
     * Creamos para esto un nuevo objeto NewUpdateUbicacionRequest (que es igual que Ubicacion) y utilizamos
     * el id de una de las ubicaciones creadas la inicio del test para modificar su contenido
     */
    @Test
    void updateUbicacion(){
        NewUpdateUbicacionRequest peticion = new NewUpdateUbicacionRequest("la silla","08300","Carrer Zanahoria 15 s/n");
        ubicacion2 = ubicacionService.updateUbicacion(peticion,ubicacion2.getId());
        System.out.println(ubicacion2.toString());//con esto vemos que el id es 6
        assertEquals("08300",ubicacion2.getCodigo_postal()); //comprobasmo que exista


    }



}
