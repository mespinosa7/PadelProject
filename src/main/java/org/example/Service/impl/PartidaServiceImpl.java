package org.example.Service.impl;
import lombok.AllArgsConstructor;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Pareja;
import org.example.Model.Partida;
import org.example.Model.User;
import org.example.Repository.PartidaRepository;
import org.example.Service.JugadorService;
import org.example.Service.ParejaService;
import org.example.Service.PartidaService;
import org.example.Service.UbicacionService;
import org.example.payload.request.NewPartidaRequest;
import org.example.payload.response.PartidaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

@AllArgsConstructor
public class PartidaServiceImpl implements PartidaService {
    private final PartidaRepository partidaRepository;
    private final JugadorService jugadorService;
    private final ParejaService parejaService;
    private final UbicacionService ubicacionService;


    @Override
    public List<Partida> findAll() {
        List<Partida> partidas =  partidaRepository.findAll();
        return partidas;
    }

    @Override
    public PartidaResponse findByUsername(String username1) {
        User jugador=jugadorService.findByUsername(username1);
        List<Partida> partidasGanadas=partidaRepository.findByParejaGanadora_Jugador1_UsernameOrParejaGanadora_Jugador2_Username(username1);
        List<Partida> partidasPerdidas=partidaRepository.findByParejaPerdedora_Jugador1_UsernameOrParejaPerdedora_Jugador2_Username(username1);

        return new PartidaResponse(partidasGanadas,partidasPerdidas,partidasPerdidas.size(),partidasGanadas.size(),Integer.sum(partidasGanadas.size(),partidasPerdidas.size()));

    }

    @Override
    public Partida findById(Long id) {
        Optional<Partida> partida= partidaRepository.findById(id);
        if(partida.isEmpty()){
            throw new NotFoundException("No existe la partida");
        }
        return partida.get();
    }

    @Override
    public boolean deleteById(Long id) {

        try{
            partidaRepository.deleteById(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Partida insertPartida(NewPartidaRequest newPartidaRequest) throws Exception {
        Pareja parejaPerdedora = parejaService.findById(newPartidaRequest.getIdParejaPerdedora());
        Pareja parejaGanadora = parejaService.findById(newPartidaRequest.getIdParejaGanadora());
        Partida partida = new Partida(parejaGanadora, parejaPerdedora, ubicacionService.findById(newPartidaRequest.getIdUbicacion()), newPartidaRequest.getDia(), newPartidaRequest.getResultado());
        return partidaRepository.save(partida);
    }



    @Override
    public Partida updatePartida(NewPartidaRequest newPartidaRequest, Long id) throws Exception {
        Pareja parejaPerdedora=parejaService.findById(newPartidaRequest.getIdParejaPerdedora());
        Pareja parejaGanadora=parejaService.findById(newPartidaRequest.getIdParejaGanadora());
        Partida partida=findById(id);
        partida.setParejaGanadora(parejaGanadora);
        partida.setParejaPerdedora(parejaPerdedora);
        partida.setDia(newPartidaRequest.getDia());
        partida.setResultado(newPartidaRequest.getResultado());
        partida.setUbicacion(ubicacionService.findById(newPartidaRequest.getIdUbicacion()));
        return partidaRepository.save(partida);

    }




}
