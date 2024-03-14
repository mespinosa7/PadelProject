package org.example.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Esta clase actúa como un punto de entrada para las solicitudes no autenticadas en el contexto de seguridad de Spring.
 */
@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    /**
     * Este método se invoca cuando se detecta un error de autenticación durante el procesamiento de una solicitud HTTP.
     *
     * @param request       La solicitud HTTP que generó el error de autenticación.
     * @param response      La respuesta HTTP que se enviará al cliente.
     * @param authException La excepción de autenticación que se produjo.
     * @throws IOException      Si ocurre un error de entrada/salida al escribir en el cuerpo de la respuesta.
     * @throws ServletException Si ocurre un error de servlet durante el procesamiento de la solicitud.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}