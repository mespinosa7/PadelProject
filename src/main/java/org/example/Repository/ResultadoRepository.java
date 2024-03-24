package org.example.Repository;

import org.example.Enums.ERole;
import org.example.Model.Resultado;
import org.example.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad de roles en la base de datos.
 */
@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

}
