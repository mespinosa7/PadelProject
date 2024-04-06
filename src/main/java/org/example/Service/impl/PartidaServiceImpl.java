package org.example.Service.impl;
import lombok.AllArgsConstructor;
import org.example.DTOs.PartidaDTO;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Pareja;
import org.example.Model.Partida;
import org.example.Model.User;
import org.example.Repository.PartidaRepository;
import org.example.Service.JugadorService;
import org.example.Service.ParejaService;
import org.example.Service.PartidaService;
import org.example.Service.UbicacionService;
import org.example.Utilidades.PatternValidator;
import org.example.mapper.PartidaMapper;
import org.example.payload.request.NewPartidaRequest;
import org.example.payload.response.PartidaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

@AllArgsConstructor
public class PartidaServiceImpl implements PartidaService {
    private final PartidaRepository partidaRepository;
    private final JugadorService jugadorService;
    private final ParejaService parejaService;
    private final UbicacionService ubicacionService;
    private final PartidaMapper partidaMapper =new PartidaMapper();

    @Override
    public List<Partida> findAll() {
        List<Partida> partidas =  partidaRepository.findAll();
        return partidas;
    }

    @Override
    public PartidaResponse findByUsernameId(Long usernameId) {
        User jugador=jugadorService.findById(usernameId);
        List<PartidaDTO> partidasGanadas=partidaRepository.findByParejaGanadora_Jugador1_UsernameOrParejaGanadora_Jugador2_Username(jugador.getUsername())
                .stream().map(e->partidaMapper.mapPartidaToPartidaDto(e)).collect(Collectors.toList());
        List<PartidaDTO> partidasPerdidas=partidaRepository.findByParejaPerdedora_Jugador1_UsernameOrParejaPerdedora_Jugador2_Username(jugador.getUsername())
                .stream().map(e->partidaMapper.mapPartidaToPartidaDto(e)).collect(Collectors.toList());;

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

        Partida partida=new Partida();
        partida.setPareja1(parejaService.findById(newPartidaRequest.getIdPareja1()));
        partida.setPareja2(parejaService.findById(newPartidaRequest.getIdPareja2()));
        if(newPartidaRequest.getIdParejaGanadora()!=null && newPartidaRequest.getIdParejaPerdedora()!=null){
            partida.setParejaGanadora(parejaService.findById(newPartidaRequest.getIdParejaGanadora()));
            partida.setParejaPerdedora(parejaService.findById(newPartidaRequest.getIdParejaPerdedora()));
        }
        partida.setDia(newPartidaRequest.getDia());
        partida.setUbicacion(ubicacionService.findById(newPartidaRequest.getIdUbicacion()));
        if(PatternValidator.validarPattern(newPartidaRequest.getResultado())){
            partida.setResultado(newPartidaRequest.getResultado());

        }else{
            throw new Exception("El resultado de los sets no tienen el formato correcto");
        }
        return partidaRepository.save(partida);

    }



    @Override
    public Partida updatePartida(NewPartidaRequest newPartidaRequest, Long id) throws Exception {
        Partida partida=findById(id);
        String resultadoAnterior = partida.getResultado();
        partida.setPareja1(parejaService.findById(newPartidaRequest.getIdPareja1()));
        partida.setPareja2(parejaService.findById(newPartidaRequest.getIdPareja2()));
        if(newPartidaRequest.getIdParejaGanadora()!=null && newPartidaRequest.getIdParejaPerdedora()!=null){
            partida.setParejaGanadora(parejaService.findById(newPartidaRequest.getIdParejaGanadora()));
            partida.setParejaPerdedora(parejaService.findById(newPartidaRequest.getIdParejaPerdedora()));
        }
        partida.setDia(newPartidaRequest.getDia());
        if(PatternValidator.validarPattern(newPartidaRequest.getResultado())){
            partida.setResultado(newPartidaRequest.getResultado());
        }else{
            partida.setResultado(resultadoAnterior);
        }
        partida.setUbicacion(ubicacionService.findById(newPartidaRequest.getIdUbicacion()));
        return partidaRepository.save(partida);

    }

}
