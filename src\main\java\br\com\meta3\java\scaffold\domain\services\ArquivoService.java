package br.com.meta3.java.scaffold.domain.services;

/**
 * Domain service abstraction exposing legacy getter behavior for Arquivo entities.
 *
 * The legacy code exposed a getter:
 *     public int getCodigoarquivo() { return this.codigoarquivo; }
 *
 * To preserve that behavior for the application layer, this service declares a method
 * that returns the primitive int value for the arquivo's codigoarquivo given the entity id.
 *
 * Implementations are expected to:
 *  - Resolve the Arquivo by its database id (Long).
 *  - If the Arquivo is not found, decide on an appropriate failure strategy (e.g. throw a runtime exception).
 *  - Return the primitive int codigoarquivo to maintain compatibility with legacy callers.
 */

// TODO: (REVIEW) Preserve legacy primitive return type for codigoarquivo to match getCodigoarquivo()
// return 0;
public interface ArquivoService {

    /**
     * Retrieve the legacy codigoarquivo value for the Arquivo identified by the given id.
     *
     * This method intentionally returns a primitive int to mimic the original getter signature
     * from the legacy domain model. Implementations must handle the case where no Arquivo exists
     * for the provided id (for example, by throwing an appropriate runtime exception).
     *
     * @param id the database identifier of the Arquivo
     * @return the legacy codigoarquivo value (primitive int)
     * @throws IllegalArgumentException if id is null
     * @throws RuntimeException if the Arquivo with the given id cannot be found (implementation-specific)
     */
    int getCodigoarquivoById(Long id);
}