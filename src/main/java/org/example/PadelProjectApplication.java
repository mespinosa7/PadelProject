package org.example;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.example.Service.AuthService;
import org.example.Service.impl.AuthServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación PadelProject. Inicia la aplicación Spring Boot.
 */
@SpringBootApplication
public class PadelProjectApplication {
    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args Los argumentos de línea de comandos pasados al programa.
     */
    public static void main(String[] args) {

        SpringApplication.run(PadelProjectApplication.class, args);
    }


    /**
     * Método que devuelve una configuración personalizada de OpenAPI para documentar la API.
     * Es un metodo neesario para la instalacion y correcto funcionamiento de Swagger, para la parte de autorización del token
     *
     * @return Un objeto OpenAPI que especifica la configuración personalizada para la documentación de la API.
     */

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Docs").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    /**
     * Método que devuelve un CommandLineRunner utilizado para inicializar datos al iniciar la aplicación.
     * Este método será invocado por Spring al iniciar la aplicación.
     * (la propiedad de application.properties debe de estar en create(si no estaría en validate)
     *      spring.jpa.hibernate.ddl-auto=create)
     *
     * @param authService El servicio de autenticación utilizado para inicializar los datos.
     * @return CommandLineRunner que ejecutará el método initData() del servicio de autenticación al iniciar la aplicación.
     */
    /*
    @Bean
    public CommandLineRunner commandLineRunner(AuthServiceImpl authService) {
        return args -> {
            authService.initData();
        };
    }
    */

    
}