## Backend de Aplicación de Pádel

Este es el repositorio del backend para una aplicación de gestión de historial de partidas de pádel. La aplicación permite gestionar las partidas de pádel de los distintos jugadores, incluyendo la ubicación de las partidas, las parejas y jugadores participantes, así como estadísticas relacionadas.

### Tecnologías Utilizadas

- **Spring Boot**: Utilizamos Spring Boot para desarrollar la lógica del backend de forma rápida y eficiente.
- **MySQL**: La base de datos se implementa utilizando MySQL para almacenar la información de las partidas, jugadores,ubicaciones, parejas y estadísticas.
- **REST Controllers**: Utilizamos REST Controllers en Spring Boot para exponer los endpoints API que permiten la comunicación con el frontend u otras aplicaciones.
- **Pruebas Unitarias**: Se incluyen pruebas unitarias para garantizar el correcto funcionamiento de las diferentes funcionalidades.

### Estructura del Proyecto

El proyecto sigue una estructura estándar de una aplicación Spring Boot, con los siguientes componentes principales:

- `src/main/java`: Contiene el código fuente de la aplicación.
  - `com.example`: Paquete base de la aplicación.
    - `controller`: Contiene los controladores REST que definen los endpoints de la API.
    - `model`: Define las entidades del dominio, como `Partida`, `Jugador`, `Pareja`, etc.
    - `repository`: Contiene las interfaces de repositorio que definen las operaciones de base de datos.
    - `service`: Contiene la lógica de negocio de la aplicación.
    - `mapper`: Contiene los mapeos entre entidades y DTOs.
    - `security`: Contiene la configuración de seguridad de la aplicación.
    - `utilidades`: Contiene clases de utilidades para la aplicación.
    - `enums`: Contiene enumeraciones utilizadas en la lógica de negocio.
    - `dto`: Contiene los objetos de transferencia de datos (DTOs) utilizados en la aplicación.
    - `payload`: Contiene los objetos de carga útil (payload) utilizados en la aplicación.
- `src/main/resources`: Contiene los archivos de configuración y recursos de la aplicación.
- `src/test`: Contiene los archivos de prueba unitaria para verificar el funcionamiento correcto de la lógica de la aplicación.

### Docuemntación
Para información técnica más detallada se puede explorar:
 - Los propios comentarios del código
 - La documentación generada con java docs (https://mespinosa7.github.io/PadelProject/)
 -La documentación generada copn swagger de los endpoints disponibles. Se tendra que ir a http://localhost:8080/swagger-ui/index.html#/ una vez arrancado el proyecto.









