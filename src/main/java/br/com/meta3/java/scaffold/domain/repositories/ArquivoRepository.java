package br.com.meta3.java.scaffold.domain.repositories;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import java.util.List;
import java.util.Optional;

/**
 * Domain repository abstraction for Arquivo CRUD operations.
 *
 * This interface decouples the application/service layer from the persistence implementation.
 * The infrastructure module should provide a concrete implementation (for example, a Spring Data JPA
 * repository adapter) that implements these methods.
 *
 * Design notes / decisions:
 * - We intentionally avoid depending on Spring Data types (e.g., JpaRepository, Pageable) in the
 *   domain layer to keep the domain pure and portable.
 * - Numeric identifiers use Integer (boxed) to align with the domain entity which uses Integer and
 *   to allow null values before persistence.
 * - Common query methods that make sense from the legacy model are provided (findByCodigoescola,
 *   findByAnovigencia). Additional queries should be added here as needed by business requirements.
 *
 * TODO: (REVIEW) If pagination or sorting becomes required in the application, consider adding
 * abstracted types for paging (or introduce small page/sort DTOs in the domain) instead of leaking
 * Spring Data types into the domain.
 */
public interface ArquivoRepository {

    /**
     * Persist a new Arquivo or update an existing one.
     *
     * @param arquivo entity to save
     * @return persisted entity (with id populated for new entities)
     */
    Arquivo save(Arquivo arquivo);

    /**
     * Find an Arquivo by its primary key (codigoarquivo).
     *
     * @param codigoarquivo primary key
     * @return optional with entity if found
     */
    Optional<Arquivo> findById(Integer codigoarquivo);

    /**
     * Return all Arquivo entities.
     *
     * @return list of Arquivo
     */
    List<Arquivo> findAll();

    /**
     * Delete an Arquivo by its primary key.
     *
     * @param codigoarquivo primary key
     */
    void deleteById(Integer codigoarquivo);

    /**
     * Check existence by primary key.
     *
     * @param codigoarquivo primary key
     * @return true if exists
     */
    boolean existsById(Integer codigoarquivo);

    /**
     * Find all Arquivo entities for a given school code.
     *
     * Note: Matching semantics (exact/like) should be defined by the infrastructure implementation.
     *
     * @param codigoescola school code
     * @return list of Arquivo matching the school code
     */
    List<Arquivo> findByCodigoescola(String codigoescola);

    /**
     * Find all Arquivo entities for a given year of validity.
     *
     * @param anovigencia year string
     * @return list of Arquivo matching the year
     */
    List<Arquivo> findByAnovigencia(String anovigencia);

    /**
     * Returns a count of all Arquivo records.
     *
     * @return total count
     */
    long count();
}