package org.example.Service.impl;
import lombok.AllArgsConstructor;
import org.example.Exceptions.NotFoundException;
import org.example.Model.Ubicacion;
import org.example.Repository.UbicacionRepository;
import org.example.Service.UbicacionService;
import org.example.payload.request.NewUpdateUbicacionRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

@AllArgsConstructor
public class UbicacionServiceImpl  implements UbicacionService {
    private final UbicacionRepository ubicacionRepository;


    @Override
    public List<Ubicacion> findAll() {
        ArrayList<Ubicacion> ubicaciones =  (ArrayList<Ubicacion>)ubicacionRepository.findAll();
        return ubicaciones;
    }

    @Override
    public Ubicacion findByName(String name) {
        return ubicacionRepository.findByName(name).get();
    }

    @Override
    public boolean deleteByName(String name) {
        Boolean delete=false;
        try{
            ubicacionRepository.deleteByName(name);
        }catch(Exception e){
            delete=false;
        }
        return delete;
    }

    @Override
    public Ubicacion updateUbicacion(NewUpdateUbicacionRequest ubicacion, String name) {        // Busca el jugador por su nombre de usuario
        Ubicacion existingUbicacion = findByName(name);
        if (existingUbicacion == null) {
            // El jugador no existe, devuelve un error
            throw new NotFoundException("Error: Ubicacion not found!");
        }else{
            // Actualiza la información del jugador
            existingUbicacion.setName(ubicacion.getName());
            existingUbicacion.setCodigo_postal(ubicacion.getCodigo_postal());
            existingUbicacion.setDireccion(ubicacion.getDireccion());

        }

        return ubicacionRepository.save(existingUbicacion);
    }
/*
    @Override
    public Ubicacion insertUbicacion(Ubicacion ubicacion) {
        Ubicacion existingUbicacion = ubicacion;
        if (existingUbicacion != null) {
            // El jugador no existe, devuelve un error
            throw new NotFoundException("Error: Ubicacion not found!");
        }else{
            // Actualiza la información del jugador
            existingUbicacion.setName(ubicacion.getName());
            existingUbicacion.setCodigo_postal(ubicacion.getCodigo_postal());
            existingUbicacion.setDireccion(ubicacion.getDireccion());

        }

        return ubicacionRepository.save(existingUbicacion);
    }
*/

}
