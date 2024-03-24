package org.example.Service.impl;
import lombok.AllArgsConstructor;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Pareja;
import org.example.Model.User;
import org.example.Repository.JugadorRepository;
import org.example.Repository.ParejaRepository;
import org.example.Service.JugadorService;
import org.example.Service.ParejaService;
import org.example.payload.request.NewParejaRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service

@AllArgsConstructor
public class ParejaServiceImpl implements ParejaService {
    private final ParejaRepository parejaRepository;
    private final JugadorService jugadorService;
    private final JugadorRepository jugadorRepository;


    @Override
    public List<Pareja> findAll() {
        ArrayList<Pareja> parejas =  (ArrayList<Pareja>)parejaRepository.findAll();
        return parejas;
    }

    @Override
    public Pareja findByUsernames(String username1, String username2) {
        Optional<Pareja> pareja=parejaRepository.findByJugador1AndJugador2(username1,username2);
        if(pareja.isPresent()){
            return pareja.get();
        }else{
            throw new NotFoundException("No hay ninguna pareja con estos dos jugadores");
        }

    }

    @Override
    public List<Pareja> findParejasByJugador(String username) {
        User jugador=jugadorService.findByUsername(username);
        List<Pareja> totalParejas=jugador.getParejasComoJugador1();
        totalParejas.addAll(jugador.getParejasComoJugador2());
        return totalParejas;
    }

    @Override
    public Pareja findById(Long id) {
        Optional<Pareja> pareja=parejaRepository.findById(id);
        if(pareja.isEmpty()){
            throw new NotFoundException("La pareja no existe");
        }
        return pareja.get();
    }

    @Override
    public boolean deleteById(Long id) {

        try{
            parejaRepository.deleteById(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Pareja insertPareja(NewParejaRequest newParejaRequest) throws Exception {
        User jugador1=jugadorService.findByUsername(newParejaRequest.getUsername1());
        User jugador2=jugadorService.findByUsername(newParejaRequest.getUsername2());

        Optional<Pareja> parejaExistente=parejaRepository.findByJugador1AndJugador2(jugador1.getUsername(),jugador2.getUsername());
        if(parejaExistente.isPresent()){
            throw new Exception("Esta pareja ya existe");
        }else if(jugador1.equals(jugador2)) {
            throw new Exception("No puede haber una pareja compuesto por dos veces el mimso usuario");
        }else{
            Pareja pareja=new Pareja(jugador1,jugador2);
            parejaRepository.save(pareja);
            jugadorRepository.save(jugador1);
            jugadorRepository.save(jugador2);
            return pareja;
        }
    }


}
