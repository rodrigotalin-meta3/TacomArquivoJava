package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.ArquivoResponseDto;
import br.com.meta3.java.scaffold.domain.services.ArquivoService;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing legacy Arquivo codigoarquivo via HTTP.
 *
 * Endpoint:
 *   GET /arquivos/{id}/codigoarquivo
 *
 * Behavior:
 * - Delegates to ArquivoService#getCodigoarquivoById(Long) which preserves legacy primitive int return.
 * - Returns 200 with ArquivoResponseDto on success.
 * - Returns 400 if client provides invalid input (maps IllegalArgumentException).
 * - Returns 404 if the Arquivo cannot be found (maps RuntimeException thrown by the service).
 */
@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    // TODO: (REVIEW) Use constructor injection to keep controller testable and to follow Spring best-practices
    // Objects.requireNonNull(arquivoService, "arquivoService must not be null");
    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = Objects.requireNonNull(arquivoService, "arquivoService must not be null");
    }

    /**
     * Expose the legacy getCodigoarquivo() over HTTP.
     *
     * Example response body: { "codigoarquivo": 123 }
     *
     * @param id database identifier of the Arquivo
     * @return 200 + ArquivoResponseDto when found; 400 for invalid input; 404 when not found
     */
    @GetMapping("/{id}/codigoarquivo")
    public ResponseEntity<ArquivoResponseDto> getCodigoarquivo(@PathVariable("id") Long id) {
        // TODO: (REVIEW) Preserve legacy service contract which throws IllegalArgumentException for null id
        // arquivoService.getCodigoarquivoById(id);
        try {
            int codigo = arquivoService.getCodigoarquivoById(id);

            // TODO: (REVIEW) Preserve legacy primitive getter semantics in API by returning a DTO that
            // exposes the same primitive campo and getter name expected by legacy consumers
            // new ArquivoResponseDto(codigo)
            ArquivoResponseDto dto = new ArquivoResponseDto(codigo);

            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            // TODO: (REVIEW) Map legacy IllegalArgumentException (invalid input) to HTTP 400 Bad Request
            // ResponseEntity.badRequest().build()
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            // TODO: (REVIEW) Map legacy RuntimeException thrown when Arquivo not found to HTTP 404
            // ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}