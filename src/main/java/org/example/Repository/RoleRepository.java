package org.example.Repository;

import org.example.Enums.ERole;
import org.example.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repositorio para la entidad de roles en la base de datos.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Busca un rol por su nombre.
     * @param name Nombre del rol a buscar.
     * @return Optional que puede contener el rol encontrado, si existe.
     */
    Optional<Role> findByName(ERole name);
    /**
     * Busca un rol por su identificador único.
     * @param id Identificador único del rol a buscar.
     * @return Optional que puede contener el rol encontrado, si existe.
     */
    Optional<Role> findById(Long id);
}
