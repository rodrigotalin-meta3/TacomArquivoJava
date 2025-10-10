package br.com.meta3.java.scaffold.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meta3.java.scaffold.domain.entities.Escola;

/**
 * Spring Data repository for the Escola entity.
 *
 * Notes:
 * - Extends JpaRepository to provide standard CRUD and paging/sorting operations.
 * - Keeps the repository in the domain layer as an abstraction for persistence operations.
 *
 * - Legacy compatibility:
 *   The legacy entity exposed a non-standard getter name getCodigoescola() (lowercase 'e' after codigo).
 *   To preserve compatibility with legacy code and to avoid subtle lookup issues, two finder signatures are provided:
 *     - findByCodigoescola(...) -> matches property name derived from getCodigoescola()
 *     - findByCodigoEscola(...) -> matches conventional camelCase property (if used)
 *
 *   Providing both methods increases robustness against naming differences between code that may reference either style.
 *
 * TODO: (REVIEW) If more domain-specific queries are required (e.g., by other fields or with pagination/sorting),
 * add them here or move complex queries to a custom repository implementation.
 */
@Repository
public interface EscolaRepository extends JpaRepository<Escola, Integer> {

    /**
     * Find an Escola by the legacy-styled property name (matches getCodigoescola()).
     *
     * Returning Optional to express presence/absence and avoid nulls in callers.
     */
    Optional<Escola> findByCodigoescola(Integer codigoescola);

    /**
     * Find an Escola by the conventional camelCase property name (matches getCodigoEscola()).
     *
     * Having both methods increases robustness against naming differences between code
     * that may reference either property style.
     */
    Optional<Escola> findByCodigoEscola(Integer codigoEscola);
    
    // TODO: (REVIEW) Consider adding custom query methods or @Query annotations if the database
    // column names differ from property names or if performance-sensitive queries are needed.
}