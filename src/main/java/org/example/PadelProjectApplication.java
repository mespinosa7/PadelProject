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
    @Bean
    public CommandLineRunner commandLineRunner(AuthServiceImpl authService) {
        return args -> {
            authService.initData();
        };
    }
    
}