package org.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
}