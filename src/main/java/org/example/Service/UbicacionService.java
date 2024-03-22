package org.example.Service;

import org.example.Model.Ubicacion;
import org.example.payload.request.NewUpdateUbicacionRequest;

import javax.validation.Valid;
import java.util.List;

public interface UbicacionService {
    /**
     * Recupera todas los ubicaciones.
     * @return Lista de todas las ubicaciones.
     */
    public List<Ubicacion> findAll();
    /**
     * Busca la ubicacion por su nombre.
     * @param name Nombre de usuario del jugador a buscar.
     * @return El jugador encontrado.
     */
    public Ubicacion findByName(String name);
    /**
     * Elimina una ubicacion por su nombre.
     * @param name Nombre de la ubicacion a eliminar.
     * @return true si se elimina correctamente, false de lo contrario.
     */
    //public boolean deleteByName(String name);

    boolean deleteByName(String name);

    /**
     * Actualiza la información de una ubicacion.
     * @param ubicacion Objeto Ubicacion con la información actualizada de la ubicacion.
     * @return La ubicacion actualizada.
     */
    public Ubicacion updateUbicacion(@Valid NewUpdateUbicacionRequest ubicacion, String name);
    /**
     * Inserta una nueva ubicacion.
     * @param ubicacion Objeto User con la información de la nueva ubicacion.
     * @return La nueva ubicacion inseratada.
     */
    //public Ubicacion insertUbicacion(Ubicacion ubicacion);


}
