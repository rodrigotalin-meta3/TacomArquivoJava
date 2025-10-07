package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.CodigoArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for Arquivo related endpoints.
 * Exposes endpoint to update the 'codigoarquivo' property (migrated from legacy setter).
 */
@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * Update only the 'codigoarquivo' field of an Arquivo resource.
     *
     * Accepts a CodigoArquivoDto validated by Jakarta Validation and delegates to ArquivoService.updateCodigoArquivo.
     * Returns 200 OK when update succeeds, or 404 Not Found when the resource does not exist.
     */
    @PutMapping("/{id}/codigo")
    public ResponseEntity<Void> updateCodigoArquivo(
            @PathVariable("id") Long id,
            @Valid @RequestBody CodigoArquivoDto codigoArquivoDto) {

        // TODO: (REVIEW) Delegating legacy setter behavior (setCodigoarquivo) to ArquivoService.updateCodigoArquivo
        // ArquivoService is responsible for finding the entity, applying the codigo change and persisting.
        arquivoService.updateCodigoArquivo(id, codigoArquivoDto);

        // The service is expected to throw EntityNotFoundException when the entity is not found.
        // We catch that below to translate it into a 404 response.
        try {
            // TODO: (REVIEW) Calling service method that encapsulates legacy setter semantics
            arquivoService.updateCodigoArquivo(id, codigoArquivoDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            // TODO: (REVIEW) Translating persistence-layer 'not found' into HTTP 404 for API consumers
            return ResponseEntity.notFound().build();
        }
    }
}