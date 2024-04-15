package org.example.Service;

import org.example.Model.Ubicacion;
import org.example.payload.request.LoginRequest;
import org.example.payload.request.NewUpdateUbicacionRequest;
import org.example.payload.request.SignupJugadorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
/**
 * Interfaz que define los métodos para la autenticación de usuarios y el registro de jugadores.
 */
public interface AuthService {
    /**
     * Autentica a un usuario utilizando las credenciales proporcionadas en la solicitud.
     *
     * @param loginRequest La solicitud de inicio de sesión.
     * @return Una respuesta que contiene el token JWT si la autenticación es exitosa.
     */
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);
    /**
     * Registra un nuevo jugador en el sistema.
     *
     * @param signUpJugadorRequest La solicitud de registro de jugador.
     * @return Una respuesta que indica si el usuario se registró correctamente.
     */
    public ResponseEntity<?> registrarJugador(SignupJugadorRequest signUpJugadorRequest);
    /**
     * Inicializa datos de prueba en el sistema, como roles de usuario y jugadores.
     */
    public void initData();




}
