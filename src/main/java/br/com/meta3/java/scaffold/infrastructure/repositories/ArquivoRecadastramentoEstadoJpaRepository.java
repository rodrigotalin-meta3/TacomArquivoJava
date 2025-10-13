package br.com.meta3.java.scaffold.infrastructure.repositories;

import br.com.meta3.java.scaffold.domain.entities.ArquivoRecadastramentoEstado;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRecadastramentoEstadoRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data JPA adapter for the domain ArquivoRecadastramentoEstadoRepository.
 *
 * This interface bridges the domain-layer repository abstraction (br.com.meta3.java.scaffold.domain.repositories.ArquivoRecadastramentoEstadoRepository)
 * to Spring Data JPA by extending JpaRepository. Spring will provide the implementation at runtime.
 *
 * Design notes:
 * - We preserve the domain repository method signatures by implementing the domain interface.
 * - Query derivation methods for anoBase, cnpj and bairro are declared so Spring Data will
 *   generate appropriate queries automatically.
 *
 * TODO: (REVIEW) Matching semantics:
 * - Currently these methods use exact matching (e.g., findByAnoBase) which maps to equality in SQL.
 * - If case-insensitive or partial (LIKE) matching is desired, consider replacing with:
 *     List<ArquivoRecadastramentoEstado> findByBairroContainingIgnoreCase(String bairro);
 *   or annotate with @Query for custom SQL.
 *
 * TODO: (REVIEW) CNPJ normalization:
 * - Legacy data may contain differently formatted CNPJ values (with punctuation).
 *   If normalization is required, either store normalized values or add a custom query that
 *   strips punctuation before comparing.
 */
@Repository
@Transactional(readOnly = true)
public interface ArquivoRecadastramentoEstadoJpaRepository
        extends JpaRepository<ArquivoRecadastramentoEstado, String>, ArquivoRecadastramentoEstadoRepository {

    /**
     * Find all records matching the given anoBase (year).
     *
     * Note: This derived query performs exact matching. For partial or case-insensitive
     * searches adapt the method name or provide a @Query implementation.
     */
    @Override
    List<ArquivoRecadastramentoEstado> findByAnoBase(String anoBase);

    /**
     * Find all records matching the given CNPJ.
     *
     * Note: CNPJ formatting may vary in legacy data. Consider normalizing values if needed.
     */
    @Override
    List<ArquivoRecadastramentoEstado> findByCnpj(String cnpj);

    /**
     * Find all records matching the given bairro (neighbourhood).
     *
     * Note: For user-friendly searches you might want to switch to containing/ignore-case variants.
     */
    @Override
    List<ArquivoRecadastramentoEstado> findByBairro(String bairro);

    // JpaRepository already provides implementations for save, findById, findAll, deleteById,
    // existsById and count which satisfy the domain interface contract.
}