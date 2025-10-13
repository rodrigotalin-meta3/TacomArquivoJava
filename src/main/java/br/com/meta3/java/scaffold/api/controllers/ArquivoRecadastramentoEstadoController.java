package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoRecadastramentoEstadoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoRecadastramentoEstadoService;
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
 * REST controller exposing endpoints for ArquivoRecadastramentoEstado resources.
 *
 * Endpoints:
 * - GET  /api/recadastramento-estado          -> list all records
 * - GET  /api/recadastramento-estado/{codigo} -> retrieve a record by codigo (natural key)
 * - POST /api/recadastramento-estado          -> create a new record
 *
 * Design notes:
 * - Uses ArquivoRecadastramentoEstadoService for business logic and validation.
 * - On successful creation returns 201 Created with Location header pointing to the new resource.
 * - Exception handlers mirror conventions used in ArquivoController to translate common service exceptions.
 */
@RestController
@RequestMapping("/api/recadastramento-estado")
public class ArquivoRecadastramentoEstadoController {

    private final ArquivoRecadastramentoEstadoService service;

    public ArquivoRecadastramentoEstadoController(ArquivoRecadastramentoEstadoService service) {
        this.service = service;
    }

    /**
     * List all ArquivoRecadastramentoEstado records.
     *
     * @return list of DTOs
     */
    @GetMapping
    public ResponseEntity<List<ArquivoRecadastramentoEstadoDto>> listAll() {
        List<ArquivoRecadastramentoEstadoDto> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Retrieve a record by its natural key 'codigo'.
     *
     * @param codigo natural identifier
     * @return DTO if found or 404 Not Found
     */
    @GetMapping("/{codigo}")
    public ResponseEntity<ArquivoRecadastramentoEstadoDto> getByCodigo(@PathVariable("codigo") String codigo) {
        try {
            ArquivoRecadastramentoEstadoDto dto = service.findById(codigo);
            return ResponseEntity.ok(dto);
        } catch (NoSuchElementException ex) {
            // Also handled by the global handler below; returning 404 here for clarity.
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new ArquivoRecadastramentoEstado.
     *
     * Note: Service performs Create-group validation and business rules (e.g., uniqueness of codigo).
     *
     * @param dto incoming DTO validated at binding time
     * @return 201 Created with created DTO and Location header
     */
    @PostMapping
    public ResponseEntity<ArquivoRecadastramentoEstadoDto> create(@RequestBody @Valid ArquivoRecadastramentoEstadoDto dto) {
        // TODO: (REVIEW) Rely on service to perform authoritative validation (with groups).
        ArquivoRecadastramentoEstadoDto created = service.create(dto);

        // Build Location header pointing to the newly created resource by its natural key (codigo).
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigo}")
                .buildAndExpand(created.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    // --- Exception handlers ---

    /**
     * Handle validation failures thrown by the service (Jakarta ConstraintViolationException).
     *
     * TransactionSystemException can wrap ConstraintViolationException when using @Transactional.
     */
    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    public ResponseEntity<String> handleValidationException(Exception ex) {
        String message = ex.getMessage();
        if (ex instanceof TransactionSystemException && ex.getCause() != null) {
            message = ex.getCause().getMessage();
        }
        // TODO: (REVIEW) Consider returning structured error responses with per-field details.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    /**
     * Handle illegal arguments from the service (e.g., duplicate codigo on create).
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
