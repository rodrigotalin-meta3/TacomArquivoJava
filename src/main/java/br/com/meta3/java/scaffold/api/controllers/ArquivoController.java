package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.CodigoArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller exposing endpoints for Arquivo operations.
 *
 * Note:
 * - Endpoints accept and return CodigoArquivoDto which includes the migrated
 *   codigoEscola field (this replaces direct use of legacy setter).
 * - Creation and update responsibilities are delegated to ArquivoService so
 *   business logic remains in the application layer.
 */
@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    // TODO: (REVIEW) Accept CodigoArquivoDto for create so the new codigoEscola field
    // is handled at the API boundary (migrated from legacy setCodigoescola setter).
    // This keeps the controller responsible only for transport concerns.
    @PostMapping
    public ResponseEntity<CodigoArquivoDto> create(@RequestBody @Valid CodigoArquivoDto codigoArquivoDto) {
        // TODO: (REVIEW) Delegate to ArquivoService to perform creation and any necessary validation/translation.
        CodigoArquivoDto created = arquivoService.create(codigoArquivoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // TODO: (REVIEW) Use CodigoArquivoDto for update requests to ensure codigoEscola can be updated
    // consistently via the service layer rather than manipulating entities directly in the controller.
    @PutMapping("/{id}")
    public ResponseEntity<CodigoArquivoDto> update(
            @PathVariable Long id,
            @RequestBody @Valid CodigoArquivoDto codigoArquivoDto) {

        // TODO: (REVIEW) Delegate update operation to ArquivoService which encapsulates business rules.
        CodigoArquivoDto updated = arquivoService.update(id, codigoArquivoDto);
        return ResponseEntity.ok(updated);
    }

    // TODO: (REVIEW) Expose a simple retrieval endpoint returning CodigoArquivoDto to allow clients
    // to fetch persisted objects including codigoEscola. This keeps API consistent with create/update.
    @GetMapping("/{id}")
    public ResponseEntity<CodigoArquivoDto> findById(@PathVariable Long id) {
        CodigoArquivoDto found = arquivoService.findById(id);
        if (found == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(found);
    }
}