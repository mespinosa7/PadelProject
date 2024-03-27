package org.example.Repository;


import org.example.Model.Ubicacion;
import org.example.Model.User;
import org.example.Service.UbicacionService;
import org.example.payload.request.NewUpdateUbicacionRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Esta clase contiene pruebas unitarias para el repositorio de Ubicaciones (UbicacionRepository).
 * Las pruebas se centran en probar el funcionamiento de los métodos del repositorio, como guardar
 * una ubicacion en la base de datos y buscar una ubicacion por su nombre o por id.
 */
@SpringBootTest
class UbicacionRepositoryTest {

    @Autowired
    private UbicacionRepository ubicacionRepository;


    private Ubicacion ubicacion;
    private Ubicacion ubicacion2;

    /**
     * Configura el entorno para las pruebas creando ubicaciones de ejemplo en la base de datos.
     */

    @BeforeEach
    void setUp() {
        //ubicacionRepository.deleteAll();
        ubicacion = ubicacionRepository.save(new Ubicacion("la mesa","08741","Carrer Masia 75 2do 3ra"));
        ubicacion2 = ubicacionRepository.save(new Ubicacion("la silla","08722","Carrer Perejil 15 s/n"));
    }

    /**
     * Limpia el entorno después de las pruebas eliminando todos las ubicaciones creados.
     */
    @AfterEach
    void tearDown() {
        ubicacionRepository.deleteAll();
    }

    /**
     * Prueba si se puede encontrar una ubiacion por su nombre en el repositorio.
     */
    @Test
    void findByName(){
        assertEquals(ubicacion,ubicacionRepository.findByName("la mesa").get());
    }
    /**
     * Prueba si se puede encontrar una ubiacion por su id en el repositorio.
     */
    @Test
    void findById(){
        assertEquals(ubicacion,ubicacionRepository.findById(ubicacion.getId()).get());

    }
    /**
     * Prueba si se puede guardar una ubicacion en el repositorio, y luego se compruba que exista buscandala
     * por su nombre.
     */
    @Test
    void save_ubicacion_successful(){
        Ubicacion ubicacion = ubicacionRepository.save(new Ubicacion("la pala","08752","Carrer lechuga 15 s/n"));
        assertEquals(ubicacion,ubicacionRepository.findByName("la pala").get());
    }
    /**
     * Prueba si se puede borrar una ubicacion en el repositorio. Primero la creamos, luego comprobamos que se ha creado
     * correctamente, luego la borramos y comprobamos que no exista, para ello comprobamos que nos devuelve una
     * excepcion de tipo NoSuchElementException
     */
    @Test
    void deleteUbicacion(){
        Ubicacion ubicacion = ubicacionRepository.save(new Ubicacion("la pala2","08751","Carrer lechuga 15 s/n"));
        System.out.println(ubicacion.toString());//con esto vemos que el id es 6
        assertEquals(ubicacion,ubicacionRepository.findByName("la pala2").get());
        ubicacionRepository.deleteById(10L);
        assertThrows(NoSuchElementException.class, () -> {
            ubicacionRepository.findByName("la pala2").get();// Ahora esto lanzará NoSuchElementException, ya que lo hemos borrado
        });
    }

    /**
     * Prueba si se puede actualizar una ubicacion en el repositorio.
     * Primero comprobamos el valor del codigo postal, luego lo modificamos y lo volvemos a comprobar.
     */
    @Test //este no funciona
    void updateUbicacion(){
        System.out.println(ubicacion.toString());//con esto vemos que el id es 6
        assertEquals("08741",ubicacion.getCodigo_postal()); //comprobasmo que exista
        ubicacion.setCodigo_postal("08700");
        assertEquals("08700",ubicacion.getCodigo_postal()); //comprobasmo que exista

    }




}
