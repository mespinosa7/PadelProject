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
    public Ubicacion findById(Long id) {
        Optional<Ubicacion> ubi=ubicacionRepository.findById(id);
        if(ubi.isEmpty()){
            throw new NotFoundException("La ubicacion no existe");
        }
        return ubi.get();
    }

    @Override
    public boolean deleteById(Long id) {
        Boolean delete=false;
        try{
            ubicacionRepository.deleteById(id);
        }catch(Exception e){
            delete=false;
        }
        return delete;
    }

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
