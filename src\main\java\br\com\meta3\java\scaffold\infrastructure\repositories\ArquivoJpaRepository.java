package br.com.meta3.java.scaffold.infrastructure.repositories;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Arquivo entities.
 *
 * Provides basic CRUD operations via JpaRepository and a convenience finder
 * to locate Arquivo by its legacy codigoarquivo field.
 */
// TODO: (REVIEW) Using Spring Data JPA repository to replace legacy DAO access for Arquivo
// ArquivoJpaRepository extends JpaRepository<Arquivo, Long]
// TODO: (REVIEW) Exposed finder by codigoarquivo to support legacy callers relying on codigoarquivo getter
// repository.findByCodigoarquivo(codigo)
@Repository
public interface ArquivoJpaRepository extends JpaRepository<Arquivo, Long> {

    /**
     * Find an Arquivo by its legacy codigoarquivo value.
     *
     * This supports legacy code paths that work with the primitive int codigoarquivo
     * and the legacy getter getCodigoarquivo().
     *
     * @param codigoarquivo legacy codigo value
     * @return optional Arquivo matching the codigoarquivo
     */
    Optional<Arquivo> findByCodigoarquivo(int codigoarquivo);
}