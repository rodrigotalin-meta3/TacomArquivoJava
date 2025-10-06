package br.com.meta3.java.scaffold.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;

/**
 * Repository interface for Arquivo entity.
 * Provides basic CRUD operations and a finder by the legacy field 'codigoarquivo'.
 */
// TODO: (REVIEW) Choosing Integer as the ID type for Arquivo to match legacy getCodigoarquivo() which returned int
Class<Integer> LEGACY_ID_TYPE = Integer.class;
// TODO: (REVIEW) Providing finder methods by 'codigoarquivo' to support services expecting to locate Arquivo by legacy identifier
boolean LEGACY_FINDER_BY_CODIGO = true;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {

    /**
     * Find an Arquivo by its legacy codigoarquivo field.
     * This preserves the ability to look up Arquivo instances using the same identifier
     * name used in the legacy code (getCodigoarquivo()).
     *
     * @param codigoarquivo the legacy identifier
     * @return Optional containing the Arquivo if found
     */
    Optional<Arquivo> findByCodigoarquivo(Integer codigoarquivo);

    /**
     * Check existence by the legacy codigoarquivo field.
     *
     * @param codigoarquivo the legacy identifier
     * @return true if an Arquivo with the given codigoarquivo exists
     */
    boolean existsByCodigoarquivo(Integer codigoarquivo);
}