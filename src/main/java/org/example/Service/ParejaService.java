package org.example.Service;

import org.example.Model.Pareja;
import org.example.Model.Ubicacion;
import org.example.Model.User;
import org.example.payload.request.NewParejaRequest;
import org.example.payload.request.NewUpdateUbicacionRequest;

import javax.validation.Valid;
import java.util.List;

public interface ParejaService {
    /**
     * Recupera todas las parejas.
     * @return Lista de todas las ubicaciones.
     */
    public List<Pareja> findAll();

    Pareja findByUsernames(String username1, String username2);

    /**
     * Recupera todas las  en las que un user forma parte.
     * @param username
     * @return Lista de todas las parejas de las que un user forma parte.
     */
    public List<Pareja> findParejasByJugador(String username);
    /**
     * Busca la pareja por su id.
     * @param id
     * @return El jugador encontrado.
     */
    public Pareja findById(Long id);
    /**
     * Elimina una pareja por su id.
     * @param id de la pareja a eliminar.
     * @return true si se elimina correctamente, false de lo contrario.
     */

    public boolean deleteById(Long id);
    /**
     * Inserta una nueva pareja.
     * @param newParejaRequest .
     * @return La nueva pareja inseratada.
     */
    public Pareja insertPareja(NewParejaRequest newParejaRequest) throws Exception;


}
