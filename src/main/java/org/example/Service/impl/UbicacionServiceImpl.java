package org.example.Service.impl;
import lombok.AllArgsConstructor;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Pareja;
import org.example.Model.Ubicacion;
import org.example.Repository.UbicacionRepository;
import org.example.Service.UbicacionService;
import org.example.payload.request.NewUpdateUbicacionRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de UbicacionService que proporciona operaciones de servicio para gestionar ubicaciones.
 * Esta clase se encarga de realizar operaciones CRUD (crear, leer, actualizar, eliminar) en las ubicaciones
 * utilizando UbicacionRepository.
 */
@Service

@AllArgsConstructor
public class UbicacionServiceImpl  implements UbicacionService {
    private final UbicacionRepository ubicacionRepository;

    /**
     * Recupera todas las ubicaciones disponibles.
     *
     * @return una lista de todas las ubicaciones disponibles.
     */
    @Override
    public List<Ubicacion> findAll() {
        ArrayList<Ubicacion> ubicaciones =  (ArrayList<Ubicacion>)ubicacionRepository.findAll();
        return ubicaciones;
    }
    /**
     * Busca una ubicación por su identificador único.
     *
     * @param id el identificador único de la ubicación a buscar.
     * @return la ubicación correspondiente al identificador dado.
     * @throws NotFoundException si la ubicación no se encuentra en la base de datos.
     */
    @Override
    public Ubicacion findById(Long id) {
        Optional<Ubicacion> ubi=ubicacionRepository.findById(id);
        if(ubi.isEmpty()){
            throw new NotFoundException("La ubicacion no existe");
        }
        return ubi.get();
    }
    /**
     * Elimina una ubicación por su identificador único.
     *
     * @param id el identificador único de la ubicación a eliminar.
     * @return true si la ubicación se eliminó correctamente, false si no se pudo eliminar.
     */
    @Override
    public boolean deleteById(Long id) {
        Boolean delete=false;
        try{
            ubicacionRepository.deleteById(id);
            delete=true;
        }catch(Exception e){
            delete=false;
        }
        return delete;
    }
    /**
     * Actualiza una ubicación existente con la información proporcionada.
     *
     * @param ubicacion el objeto que contiene la nueva información de la ubicación.
     * @param id  el identificador único de la ubicación a actualizar.
     * @return la ubicación actualizada.
     * @throws NotFoundException si la ubicación no se encuentra en la base de datos.
     */
    @Override
    public Ubicacion updateUbicacion(NewUpdateUbicacionRequest ubicacion, Long id) {        // Busca el jugador por su nombre de usuario
        Ubicacion existingUbicacion = findById(id);
        if (existingUbicacion == null) {

            throw new NotFoundException("Error: Ubicacion not found!");
        }else{
            // Actualiza la información del jugador
            existingUbicacion.setName(ubicacion.getName());
            existingUbicacion.setCodigo_postal(ubicacion.getCodigo_postal());
            existingUbicacion.setDireccion(ubicacion.getDireccion());

        }

        return ubicacionRepository.save(existingUbicacion);
    }

}
