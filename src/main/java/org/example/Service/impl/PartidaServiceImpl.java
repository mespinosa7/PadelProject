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

/**
 * Implementación del PartidaService
 */
@Service

@AllArgsConstructor
public class PartidaServiceImpl implements PartidaService {
    private final PartidaRepository partidaRepository;
    private final JugadorService jugadorService;
    private final ParejaService parejaService;
    private final UbicacionService ubicacionService;
    private final PartidaMapper partidaMapper =new PartidaMapper();
    /**
     * Obtiene todas las partidas.
     *
     * @return Lista de todas las partidas.
     */
    @Override
    public List<Partida> findAll() {
        List<Partida> partidas =  partidaRepository.findAll();
        return partidas;
    }
    /**
     * Encuentra las partidas de las que forma parte un usuario.
     *
     * @param usernameId El ID del usuario.
     * @return La respuesta de la solicitud de partida.
     */
    @Override
    public PartidaResponse findByUsernameId(Long usernameId) {
        User jugador=jugadorService.findById(usernameId);
        List<PartidaDTO> partidasGanadas=partidaRepository.findByParejaGanadora_Jugador1_UsernameOrParejaGanadora_Jugador2_Username(jugador.getUsername())
                .stream().map(e->partidaMapper.mapPartidaToPartidaDto(e)).collect(Collectors.toList());
        List<PartidaDTO> partidasPerdidas=partidaRepository.findByParejaPerdedora_Jugador1_UsernameOrParejaPerdedora_Jugador2_Username(jugador.getUsername())
                .stream().map(e->partidaMapper.mapPartidaToPartidaDto(e)).collect(Collectors.toList());;

        return new PartidaResponse(partidasGanadas,partidasPerdidas,partidasPerdidas.size(),partidasGanadas.size(),Integer.sum(partidasGanadas.size(),partidasPerdidas.size()));

    }
    /**
     * Encuentra una partida por su identificador único.
     *
     * @param id Identificador único de la partida.
     * @return La partida encontrada, si existe.
     * @throws NotFoundException si no se encuentra ninguna partida con el identificador especificado.
     */
    @Override
    public Partida findById(Long id) {
        Optional<Partida> partida= partidaRepository.findById(id);
        if(partida.isEmpty()){
            throw new NotFoundException("No existe la partida");
        }
        return partida.get();
    }
    /**
     * Elimina una partida por su identificador único.
     *
     * @param id Identificador único de la partida a eliminar.
     * @return true si la partida se eliminó correctamente, false de lo contrario.
     */
    @Override
    public boolean deleteById(Long id) {

        try{
            partidaRepository.deleteById(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    /**
     * Inserta una nueva partida en la base de datos.
     *
     * @param newPartidaRequest La solicitud de creación de nueva partida.
     * @return La partida creada.
     * @throws Exception si la partida ya existe, si los jugadores se encuentran en ambas parejas rivales,
     *                   o si el formato del resultado de los sets no es correcto.
     */
    @Override
    public Partida insertPartida(NewPartidaRequest newPartidaRequest) throws Exception {

        Partida partida=new Partida();
        partida.setPareja1(parejaService.findById(newPartidaRequest.getPareja1()));
        partida.setPareja2(parejaService.findById(newPartidaRequest.getPareja2()));
        if(partida.getPareja1().getJugador1().equals(partida.getPareja2().getJugador1())||partida.getPareja1().getJugador1().equals(partida.getPareja2().getJugador2())
        ||partida.getPareja1().getJugador2().equals(partida.getPareja2().getJugador1())||partida.getPareja1().getJugador2().equals(partida.getPareja2().getJugador2())){
            throw new Exception("Uno de los jugadores de la partida se encuentra en las dos parejas rivales");
        }
        if(newPartidaRequest.getParejaGanadora()!=null){
            if(newPartidaRequest.getParejaGanadora().equals(partida.getPareja1().getId())||newPartidaRequest.getParejaGanadora().equals(partida.getPareja2().getId())){
                partida.setParejaGanadora(parejaService.findById(newPartidaRequest.getParejaGanadora()));
                partida.setParejaPerdedora(partida.getParejaGanadora().equals(partida.getPareja1())?partida.getPareja2():partida.getPareja1());
            }else{
                throw new Exception("La pareja ganadora no se encuentra entre las participantes!");
            }

        }
        partida.setDia(newPartidaRequest.getDia());
        partida.setUbicacion(ubicacionService.findById(newPartidaRequest.getUbicacion()));
        if(PatternValidator.validarPattern(newPartidaRequest.getResultado())){
            partida.setResultado(newPartidaRequest.getResultado());

        }else{
            throw new Exception("El resultado de los sets no tienen el formato correcto");
        }
        return partidaRepository.save(partida);

    }


    /**
     * Actualiza una partida existente en la base de datos.
     *
     * @param newPartidaRequest La solicitud de actualización de partida.
     * @param id  El ID de la partida a actualizar.
     * @return La partida actualizada.
     * @throws Exception si los jugadores se encuentran en ambas parejas rivales
     *  o si el formato del resultado de los sets no es correcto.
     */
    @Override
    public Partida updatePartida(NewPartidaRequest newPartidaRequest, Long id) throws Exception {
        Partida partida=findById(id);
        String resultadoAnterior = partida.getResultado();
        partida.setPareja1(parejaService.findById(newPartidaRequest.getPareja1()));
        partida.setPareja2(parejaService.findById(newPartidaRequest.getPareja2()));
        if(partida.getPareja1().getJugador1().equals(partida.getPareja2().getJugador1())||partida.getPareja1().getJugador1().equals(partida.getPareja2().getJugador2())
                ||partida.getPareja1().getJugador2().equals(partida.getPareja2().getJugador1())||partida.getPareja1().getJugador2().equals(partida.getPareja2().getJugador2())){
            throw new Exception("Uno de los jugadores de la partida se encuentra en las dos parejas rivales");
        }
        if(newPartidaRequest.getParejaGanadora()!=null){

            if(newPartidaRequest.getParejaGanadora().equals(partida.getPareja1().getId())||newPartidaRequest.getParejaGanadora().equals(partida.getPareja2().getId())){
                partida.setParejaGanadora(parejaService.findById(newPartidaRequest.getParejaGanadora()));
                partida.setParejaPerdedora(partida.getParejaGanadora().equals(partida.getPareja1())?partida.getPareja2():partida.getPareja1());
            }else{
                throw new Exception("La pareja ganadora no se encuentra entre las participantes!");
            }

        }
        partida.setDia(newPartidaRequest.getDia());
        if(PatternValidator.validarPattern(newPartidaRequest.getResultado())){
            partida.setResultado(newPartidaRequest.getResultado());
        }else{
            throw new Exception("El resultado de los sets no tienen el formato correcto");
        }
        partida.setUbicacion(ubicacionService.findById(newPartidaRequest.getUbicacion()));
        return partidaRepository.save(partida);

    }

}
