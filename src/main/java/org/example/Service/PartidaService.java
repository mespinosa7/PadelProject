package org.example.Service;

import org.example.Model.Pareja;
import org.example.Model.Partida;
import org.example.payload.request.NewParejaRequest;
import org.example.payload.request.NewPartidaRequest;
import org.example.payload.response.PartidaResponse;

import java.util.List;

public interface PartidaService {
    /**
     * Recupera todas las partidas.
     * @return Lista de todas las partidas.
     */
    public List<Partida> findAll();

    /**
     * Recupera todas las partidas en las que un user forma parte.
     * @param usernameId
     * @return Lista de todas las partidas de las que un user forma parte.
     */
    public PartidaResponse findByUsernameId(Long usernameId);
    /**
     * Busca la partida por su id.
     * @param id
     * @return El jugador encontrado.
     */
    public Partida findById(Long id);
    /**
     * Elimina una partida por su id.
     * @param id de la partida a eliminar.
     * @return true si se elimina correctamente, false de lo contrario.
     */

    public boolean deleteById(Long id);
    /**
     * Inserta una nueva partida.
     * @param newPartidaRequest .
     * @return La nueva partida inseratada.
     */
    public Partida insertPartida(NewPartidaRequest newPartidaRequest) throws Exception;

    /**
     * Actualiza una partida existente en el sistema con la información proporcionada en la solicitud.
     *
     * @param newPartidaRequest La solicitud que contiene los datos actualizados de la partida.
     * @param id El identificador único de la partida que se actualizará.
     * @return La partida actualizada.
     * @throws Exception Si ocurre algún error durante el proceso de actualización.
     */
    Partida updatePartida(NewPartidaRequest newPartidaRequest, Long id) throws Exception;
}
