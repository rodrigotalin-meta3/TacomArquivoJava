package br.com.meta3.java.scaffold.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;

/**
 * Application service for Arquivo-related operations.
 *
 * Notes:
 * - Provides a method getCodigoarquivoById(Integer id) that preserves the legacy
 *   getter naming and returns a primitive int (consistent with the legacy getter).
 * - Uses constructor injection for ArquivoRepository.
 * - Marks read operations as @Transactional(readOnly = true).
 *
 * TODO: (REVIEW) Consider centralizing exception types across services (e.g. a common
 * NotFoundException) so controllers can map them consistently to HTTP 404 responses.
 */
@Service
@Transactional(readOnly = true)
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    @Autowired
    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Returns the codigo (legacy-styled) of the Arquivo identified by the given id.
     *
     * Preserves legacy behavior by:
     * - Exposing the method name getCodigoarquivoById(...) which aligns with legacy getter naming.
     * - Returning a primitive int (mirroring the legacy getCodigoarquivo() behavior that returned int).
     *
     * @param id primary key of the Arquivo to fetch; must not be null
     * @return primitive int codigo value from the fetched Arquivo
     * @throws IllegalArgumentException if id is null
     * @throws ArquivoNotFoundException if no Arquivo is found for the provided id
     */
    public int getCodigoarquivoById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Arquivo arquivo = arquivoRepository.findById(id)
            .orElseThrow(() -> new ArquivoNotFoundException("Arquivo not found for id: " + id));

        // Use the legacy-styled getter on the entity to preserve original semantics.
        // TODO: (REVIEW) The entity's getCodigoarquivo() returns primitive int and defaults to 0 when null.
        // If business rules require distinguishing "0" from "not set", adjust entity/getter behavior and this service accordingly.
        return arquivo.getCodigoarquivo();
    }

    /**
     * Runtime exception thrown when an Arquivo is not found.
     *
     * Kept local to avoid creating additional files for this simple migration step.
     * TODO: (REVIEW) Consider extracting to a shared exceptions package for reuse across the application.
     */
    public static class ArquivoNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ArquivoNotFoundException(String message) {
            super(message);
        }
    }
}