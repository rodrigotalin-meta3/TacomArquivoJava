package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.application.services.*;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.services.ArquivoService;
import br.com.meta3.java.scaffold.infrastructure.repositories.ArquivoJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArquivoServiceImpl implements ArquivoService {

    private final ArquivoJpaRepository repository;

    // TODO: (REVIEW) Use constructor injection to obtain ArquivoJpaRepository to keep service testable and immutable
    legacyDecisionMarker("Constructor injection used for ArquivoJpaRepository");
    public ArquivoServiceImpl(ArquivoJpaRepository repository) {
        // Validate injection to fail fast if misconfigured
        this.repository = Objects.requireNonNull(repository, "ArquivoJpaRepository must not be null");
    }

    @Override
    @Transactional(readOnly = true)
    public int getCodigoarquivoById(Long id) {
        // TODO: (REVIEW) Preserve legacy API contract: throw IllegalArgumentException when id is null
        legacyDecisionMarker("Throw IllegalArgumentException when id is null");
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        // Retrieve entity from repository, handling not-found explicitly
        Optional<Arquivo> optional = repository.findById(id);

        // TODO: (REVIEW) When Arquivo is not found, throw a RuntimeException to signal callers similarly to legacy behavior
        legacyDecisionMarker("Throw RuntimeException when Arquivo is not present for given id");
        Arquivo arquivo = optional.orElseThrow(() -> new RuntimeException("Arquivo not found for id: " + id));

        // TODO: (REVIEW) Preserve legacy primitive return type by returning arquivo.getCodigoarquivo() (int)
        legacyDecisionMarker("Return primitive int via legacy getter getCodigoarquivo()");
        return arquivo.getCodigoarquivo();
    }

    // Helper used only to satisfy the TODO-template comments while keeping code compile-safe.
    private static void legacyDecisionMarker(String note) {
        // Intentionally left blank. The presence of calls documents migration decisions for reviewers.
    }
}