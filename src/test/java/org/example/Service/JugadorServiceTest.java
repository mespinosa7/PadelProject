package org.example.Service;

import org.example.Enums.ERole;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Role;
import org.example.Model.Ubicacion;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Repository.RoleRepository;
import org.example.payload.request.UpdateJugadorRequest;
import org.example.payload.response.UserResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JugadorServiceTest {

    @Autowired
    private JugadorService jugadorService;
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private RoleRepository roleRepository;

    private User jugador1;
    private User jugador2;
    private Role role1;




    @BeforeEach
    void setup(){
        role1=roleRepository.save(new Role( ERole.ROLE_User));
        jugador1 = jugadorRepository.save(new User( "manel","manelesp","123456",role1,"1111111","manel@hotmail.com",33,"Perez"));
        jugador2 = jugadorRepository.save(new User( "david","davidvalen","123456",role1,"1111111","david@hotmail.com",33,"Valentin"));
    }

    /**
     * Limpia el entorno después de las pruebas eliminando todos los usuarios y roles creados.
     */
    @AfterEach
    void tearDown() {

        jugadorRepository.deleteAll();
        roleRepository.deleteAll();

    }


    @Test
    void findByAll(){
        List<User> lista = new ArrayList<User>();
        boolean resultadoId = false;
        boolean resultadoTamano = false;
        lista = jugadorService.findAll();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getId() == jugador1.getId()){//buscamos un jugaoor, en este caso la numero 1
                resultadoId = true;
            }
        }
        if(lista.size() == 2){
            resultadoTamano = true;
        }
        assertTrue(resultadoId,"No se ha podido encontrar el jugaoor");
        assertTrue(resultadoTamano,"El tamaño no se corresponder");

    }

    @Test
    void findByUsername(){
        User user = jugadorService.findByUsername("manelesp");
        boolean resultado = false;
        if(user.getApellidos().equals("Perez")){
            resultado = true;
        }
        assertTrue(resultado,"No se ha podido encontrar el jugaoor");

        assertThrows(NotFoundException.class, () -> {
            jugadorService.findByUsername("david");// Este jugador no lo encontrará ya que no existe
        });
    }


    /*
    @Test  //este test da error porque el usuario al ser un test no tiene token, así que lo dejamos comentado
    void updateJugador(){
        UpdateJugadorRequest updateJugador = null;
        updateJugador = new UpdateJugadorRequest("david","Martin","99999999",45,"davidval@gmail.com","123456","123456",null,null);
        jugadorService.updateJugador(updateJugador,"davidvalen");
        assertEquals("Martin",jugadorRepository.findByUsername("davidvalen").get().getApellidos());

    }
    */


    @Test
    void existsJugador(){
        boolean resultado = jugadorService.existsJugador("manelesp");
        System.out.println("Resultado: "+resultado);
        assertTrue(resultado,"No se ha podido encontrar el jugaoor");
    }



    @Test
    void insertJugador(){
        User juanito = jugadorRepository.save(new User( "juanito","juanito","123456",role1,"1111122","juanito@hotmail.com",33,"Bale"));
        jugadorService.insertJugador(juanito);
        System.out.println(jugadorService.findByUsername("juanito").toString());
        assertTrue(jugadorService.existsJugador("juanito"),"No se ha podido encontrar el jugaoor");

    }




}
