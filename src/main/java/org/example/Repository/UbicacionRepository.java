package org.example.Repository;

import org.example.Model.Ubicacion;
import org.example.Model.User;
import org.example.payload.request.NewUpdateUbicacionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
/**
 * Repositorio de datos para la entidad de usuario (Jugador).
 */
@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    /**
     * Recupera todas los ubicaciones.
     * @return Lista de todas las ubicaciones.
     */
     List<Ubicacion> findAll();
    /**
     * Busca la ubicacion por su nombre.
     * @param name Nombre de usuario del jugador a buscar.
     * @return El jugador encontrado.
     */
    Optional<Ubicacion> findByName(String name);
    /**
     * Elimina una ubicacion por su nombre.
     * @param name Nombre de la ubicacion a eliminar.
     * @return true si se elimina correctamente, false de lo contrario.
     */
    void deleteByName(String name);

    /**
     * Verifica si existe una ubicacion con un nombre dado.
     * @param name Nombre de la ubicacion a verificar.
     * @return Verdadero si existe un usuario con el nombre de usuario dado, falso de lo contrario.
     */
    boolean existsByName(String name);

    void deleteById(Long id);





}
