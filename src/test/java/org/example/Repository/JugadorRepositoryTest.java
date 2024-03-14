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

@SpringBootTest
class JugadorRepositoryTest {


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JugadorRepository jugadorRepository;

    private User user1;
    private Role role1;
    private Role role2;

    @BeforeEach
    void setUp() {



        role1=roleRepository.save(new Role( ERole.ROLE_User));
        role2=roleRepository.save(new Role( ERole.ROLE_Admin));
        user1 = jugadorRepository.save(new User( "manel","manelesp","123456",role1));


    }

    @AfterEach
    void tearDown() {
        jugadorRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void save_jugador_successful() {

        User user2 = jugadorRepository.save(new User( "ignasi","manelesp1","123456",role1));
        assertEquals(user2,jugadorRepository.findByUsername("manelesp1").get());

    }

    @Test
    void findByUsername() {
        User jugador = jugadorRepository.findByUsername("manelesp").get();
        assertEquals(user1,jugador);
        assertTrue( jugadorRepository.findByUsername("Boing").isEmpty());

    }



}

