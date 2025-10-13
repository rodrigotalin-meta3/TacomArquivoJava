package br.com.meta3.java.scaffold.domain.repositories;

import br.com.meta3.java.scaffold.domain.entities.ArquivoRecadastramentoEstado;
import java.util.List;
import java.util.Optional;

/**
 * Domain repository abstraction for ArquivoRecadastramentoEstado CRUD operations and queries.
 *
 * Responsibilities:
 * - Decouple the application/service layer from any specific persistence implementation (e.g., Spring Data JPA).
 * - Provide basic CRUD operations and a small set of query methods commonly needed by the application.
 *
 * Design notes:
 * - The legacy entity used String-based natural key 'codigo' as its identifier. We preserve that here
 *   and use String as the id type in repository methods. If a surrogate id is introduced later,
 *   update signatures accordingly.
 * - We intentionally avoid introducing Spring Data or other framework-specific types in this interface
 *   to keep the domain pure and easily testable.
 * - For query methods (findByAnoBase, findByCnpj, findByBairro) the matching semantics (exact or partial)
 *   are left to the infrastructure implementation to decide. Typically, the infrastructure (e.g., JPA)
 *   will implement exact matches unless otherwise annotated with @Query or configured.
 *
 * TODO: (REVIEW)
 * - If paging/sorting becomes necessary, add abstracted paging DTOs to avoid leaking framework types.
 * - Decide and document whether query methods should be case-sensitive and whether they should perform
 *   exact or like/wildcard matching. Consider adding explicit method names (e.g., findByBairroContaining)
 *   or query parameter objects for richer semantics.
 */
public interface ArquivoRecadastramentoEstadoRepository {

    /**
     * Persist a new ArquivoRecadastramentoEstado or update an existing one.
     *
     * @param entity entity to save
     * @return persisted entity (may be the same instance or a managed instance depending on implementation)
     */
    ArquivoRecadastramentoEstado save(ArquivoRecadastramentoEstado entity);

    /**
     * Find an ArquivoRecadastramentoEstado by its primary key (codigo).
     *
     * @param codigo primary key (natural identifier)
     * @return optional with entity if found
     */
    Optional<ArquivoRecadastramentoEstado> findById(String codigo);

    /**
     * Return all ArquivoRecadastramentoEstado entities.
     *
     * @return list of entities
     */
    List<ArquivoRecadastramentoEstado> findAll();

    /**
     * Delete an ArquivoRecadastramentoEstado by its primary key.
     *
     * @param codigo primary key
     */
    void deleteById(String codigo);

    /**
     * Check existence by primary key.
     *
     * @param codigo primary key
     * @return true if exists
     */
    boolean existsById(String codigo);

    /**
     * Find all records matching the given anoBase (year).
     *
     * Note: Matching semantics (exact vs. like) should be implemented by the infrastructure layer.
     *
     * @param anoBase year string
     * @return list of matching entities
     */
    List<ArquivoRecadastramentoEstado> findByAnoBase(String anoBase);

    /**
     * Find all records matching the given CNPJ.
     *
     * Note: CNPJ formatting may vary in legacy data. The infrastructure implementation may choose
     * to normalize values or perform exact matching depending on requirements.
     *
     * @param cnpj company identifier string
     * @return list of matching entities
     */
    List<ArquivoRecadastramentoEstado> findByCnpj(String cnpj);

    /**
     * Find all records matching the given bairro (neighbourhood).
     *
     * Note: For user-friendly searches, the infrastructure may implement case-insensitive or partial
     * matching. The domain interface leaves that decision to the implementation.
     *
     * @param bairro neighbourhood string
     * @return list of matching entities
     */
    List<ArquivoRecadastramentoEstado> findByBairro(String bairro);

    /**
     * Returns a count of all ArquivoRecadastramentoEstado records.
     *
     * @return total count
     */
    long count();
}