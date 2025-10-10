package br.com.meta3.java.scaffold.infrastructure.repositories;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data JPA adapter for the domain ArquivoRepository.
 *
 * This interface bridges the domain-layer repository abstraction (br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository)
 * to Spring Data JPA by extending JpaRepository. Spring will provide the implementation at runtime.
 *
 * Design notes:
 * - We intentionally implement the domain repository as an interface that extends both JpaRepository
 *   and the domain repository abstraction. This keeps the domain decoupled while providing a concrete
 *   persistence implementation in the infrastructure layer.
 * - Method names (findByCodigoescola, findByAnovigencia) follow Spring Data naming conventions and
 *   are resolved automatically to queries based on entity mappings.
 *
 * TODO: (REVIEW) If the domain repository must remain free of Spring types in all modules, ensure
 * application/service layers depend only on the domain interface (ArquivoRepository) and that wiring
 * is done via Spring configuration. Currently this adapter implements the domain interface directly.
 */
@Repository
@Transactional(readOnly = true)
public interface ArquivoJpaRepository extends JpaRepository<Arquivo, Integer>, ArquivoRepository {

    /**
     * Find all Arquivo entities for a given school code.
     *
     * The domain interface declares this method; by declaring it here with the same signature
     * we ensure Spring Data will implement it using query derivation from the method name.
     *
     * Note: Matching semantics are exact by default. If wildcard/like semantics are required,
     * adapt the method name or add a @Query annotation.
     */
    @Override
    List<Arquivo> findByCodigoescola(String codigoescola);

    /**
     * Find all Arquivo entities for a given year of validity.
     *
     * Delegated to Spring Data query derivation.
     */
    @Override
    List<Arquivo> findByAnovigencia(String anovigencia);

    // The remaining CRUD methods (save, findById, findAll, deleteById, existsById, count)
    // are provided by JpaRepository. They satisfy the signatures declared in the domain repository.
    //
    // TODO: (REVIEW) If you need to customize transactional boundaries or add custom queries
    // (e.g., pagination, projections), consider adding a custom repository implementation class.
}