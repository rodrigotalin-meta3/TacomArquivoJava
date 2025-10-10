package br.com.meta3.java.scaffold.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.meta3.java.scaffold.domain.repositories.EscolaRepository;
import br.com.meta3.java.scaffold.domain.entities.Escola;

/**
 * Application service for Escola-related operations.
 *
 * Notes:
 * - Provides a method getCodigoescolaById(Integer id) that preserves the legacy
 *   getter naming and returns the value produced by the legacy entity getter
 *   getCodigoescola() (String) to maintain backward compatibility.
 * - Uses constructor injection for EscolaRepository.
 * - Marks read operations as @Transactional(readOnly = true).
 *
 * TODO: (REVIEW) Consider extracting common not-found exception types across services
 *       to avoid duplication and centralize controller mapping to HTTP statuses.
 */
@Service
@Transactional(readOnly = true)
public class EscolaService {

    private final EscolaRepository escolaRepository;

    @Autowired
    public EscolaService(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }

    /**
     * Returns the legacy-styled codigo of the Escola identified by the given id.
     *
     * Preserves legacy behavior by:
     * - Exposing the method name getCodigoescolaById(...) which aligns with legacy getter naming.
     * - Returning the raw value from the entity's legacy getter getCodigoescola() (String).
     *
     * @param id primary key of the Escola to fetch; must not be null
     * @return String codigoescola value from the fetched Escola (may be null depending on entity)
     * @throws IllegalArgumentException if id is null
     * @throws EscolaNotFoundException if no Escola is found for the provided id
     */
    public String getCodigoescolaById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Escola escola = escolaRepository.findById(id)
            .orElseThrow(() -> new EscolaNotFoundException("Escola not found for id: " + id));

        // Use the legacy-styled getter on the entity to preserve original semantics.
        // This ensures any legacy behavior implemented in the entity getter is honored.
        return escola.getCodigoescola();
    }

    /**
     * Runtime exception thrown when an Escola is not found.
     *
     * Kept local to avoid creating additional files for this simple migration step.
     * TODO: (REVIEW) Consider extracting to a shared exceptions package for reuse across the application.
     */
    public static class EscolaNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public EscolaNotFoundException(String message) {
            super(message);
        }
    }
}