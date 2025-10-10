package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller exposing endpoints for Arquivo resources.
 *
 * Endpoints:
 * - GET  /api/arquivos          -> list all Arquivo
 * - GET  /api/arquivos/{id}     -> retrieve an Arquivo by id
 * - POST /api/arquivos          -> create a new Arquivo
 *
 * Design notes:
 * - Validation groups are enforced inside ArquivoService (it uses the Jakarta Validator
 *   programmatically to validate Create/Update groups). The controller applies @Valid to
 *   request bodies to enable basic binding-time validation, but the authoritative validation
 *   (with groups) is performed in the service layer.
 * - On successful creation we return 201 Created with a Location header pointing to the new resource.
 * - Simple exception handlers translate common service exceptions to appropriate HTTP responses.
 */
@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * List all Arquivo records.
     *
     * @return list of ArquivoDto
     */
    @GetMapping
    public ResponseEntity<List<ArquivoDto>> listAll() {
        List<ArquivoDto> list = arquivoService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Retrieve an Arquivo by id.
     *
     * @param id primary key
     * @return ArquivoDto if found or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArquivoDto> getById(@PathVariable("id") Integer id) {
        try {
            ArquivoDto dto = arquivoService.findById(id);
            return ResponseEntity.ok(dto);
        } catch (NoSuchElementException ex) {
            // Translated by handler below as well; keeping local translation for clarity.
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new Arquivo.
     *
     * Note: The service enforces that codigoarquivo must be null when creating.
     *
     * @param dto incoming ArquivoDto (create semantics)
     * @return 201 Created with created DTO and Location header
     */
    @PostMapping
    public ResponseEntity<ArquivoDto> create(@RequestBody @Valid ArquivoDto dto) {
        // TODO: (REVIEW) Rely on ArquivoService for full Create-group validation. Controller-level
        // @Valid provides early feedback for basic constraints but group-specific validation
        // (e.g., ensuring id is null) is enforced in the service using the Validator API.
        ArquivoDto created = arquivoService.create(dto);

        // Build Location header for the newly created resource.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getCodigoarquivo())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    // --- Exception handlers ---

    /**
     * Handle validation failures thrown by the service (Jakarta ConstraintViolationException).
     *
     * Returning 400 Bad Request with the exception message gives clients basic feedback.
     * For production systems consider returning a structured payload with per-field errors.
     */
    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    public ResponseEntity<String> handleValidationException(Exception ex) {
        // TransactionSystemException can wrap ConstraintViolationException when using @Transactional.
        String message = ex.getMessage();
        if (ex instanceof TransactionSystemException && ex.getCause() != null) {
            message = ex.getCause().getMessage();
        }
        // TODO: (REVIEW) Consider exposing detailed violation information instead of a single message.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    /**
     * Handle illegal arguments from the service (e.g., client provided an id on create).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handle not found exceptions thrown by the service.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
