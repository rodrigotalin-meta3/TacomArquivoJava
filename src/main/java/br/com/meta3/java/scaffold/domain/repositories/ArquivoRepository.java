package br.com.meta3.java.scaffold.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;

/**
 * Spring Data repository for the Arquivo entity.
 *
 * Notes:
 * - Extends JpaRepository to provide standard CRUD and paging/sorting operations.
 * - Keeping repository in the domain layer as an abstraction for persistence operations.
 *
 * - Legacy compatibility:
 *   The legacy entity provided a non-standard getter name getCodigoarquivo() (lowercase 'a').
 *   Spring Data derives property names from JavaBean getters. To accommodate both naming
 *   conventions and to make repository usage flexible, two finder signatures are provided:
 *     - findByCodigoarquivo(...)  -> matches property name derived from getCodigoarquivo()
 *     - findByCodigoArquivo(...)  -> matches conventional camelCase property (if used)
 *
 *   This avoids subtle lookup issues when consumers rely on either naming style.
 *
 * TODO: (REVIEW) If more domain-specific queries are needed (e.g. by other fields or with
 * pagination/sorting), add them here or move complex queries to a custom repository implementation.
 */
@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {

    /**
     * Find an Arquivo by the legacy-styled property name (matches getCodigoarquivo()).
     *
     * Returning Optional to express presence/absence and avoid nulls in callers.
     */
    Optional<Arquivo> findByCodigoarquivo(Integer codigoarquivo);

    /**
     * Find an Arquivo by the conventional camelCase property name (matches getCodigoArquivo()).
     *
     * Having both methods increases robustness against naming differences between code
     * that may reference either property style.
     */
    Optional<Arquivo> findByCodigoArquivo(Integer codigoArquivo);
}