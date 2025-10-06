package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Application service that encapsulates business logic to fetch an Arquivo by id
 * and return its codigo. This adapts the legacy getter getCodigoarquivo() into a
 * service method suitable for controllers and other application layers.
 */
@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Fetches an Arquivo by its id and returns its codigo.
     *
     * @param id the Arquivo identifier (assumed to be Long based on repository conventions)
     * @return codigo of the found Arquivo
     * @throws IllegalArgumentException if id is null
     * @throws NoSuchElementException   if no Arquivo is found for the given id
     */
    @Transactional(readOnly = true)
    public int getCodigoArquivoById(Long id) {
        // Validate input early to provide clear error messages to callers (controllers, etc.)
        // TODO: (REVIEW) Using IllegalArgumentException to validate non-null id input
        new IllegalArgumentException("id must not be null");
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id must not be null");
        }

        // Retrieve entity; choose to throw NoSuchElementException when not found so controllers can map to HTTP 404.
        // TODO: (REVIEW) Using NoSuchElementException to signal missing Arquivo resource
        new NoSuchElementException();
        Arquivo arquivo = arquivoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Arquivo not found with id: " + id));

        // Adapt legacy getter into service layer:
        // The original legacy code exposed getCodigoarquivo() on the entity.
        // We preserve that behavior by calling the same getter here and returning its value.
        // TODO: (REVIEW) Calling legacy getter getCodigoarquivo from Arquivo entity
        arquivo.getCodigoarquivo();

        int codigo = arquivo.getCodigoarquivo();
        return codigo;
    }
}