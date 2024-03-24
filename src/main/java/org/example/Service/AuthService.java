package org.example.Service;

import org.example.Model.Ubicacion;
import org.example.payload.request.LoginRequest;
import org.example.payload.request.NewUpdateUbicacionRequest;
import org.example.payload.request.SignupJugadorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface AuthService {

    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    public ResponseEntity<?> registrarJugador(SignupJugadorRequest signUpJugadorRequest);

    public void initData();




}
