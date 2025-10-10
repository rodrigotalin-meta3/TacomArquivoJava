package br.com.meta3.java.scaffold.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.meta3.java.scaffold.application.services.ArquivoService;
import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;

/**
 * REST controller exposing Arquivo-related endpoints.
 *
 * Exposes:
 *   GET /api/arquivos/{id}/codigo
 *   GET /api/arquivos/{id}/nome
 *
 * Behavior:
 * - Uses ArquivoService to fetch the legacy-styled codigo and nome values.
 * - Returns an ArquivoDto containing the requested legacy property name
 *   ("codigoarquivo" or "nomearquivo") to preserve API compatibility with legacy clients.
 *
 * Notes / Decisions:
 * - ArquivoService#getCodigoarquivoById(...) returns a primitive int (legacy behavior).
 *   The DTO expects an Integer. We convert the primitive to Integer before creating the DTO.
 *
 * - ArquivoService#getNomearquivoById(...) returns String. We populate only the nomearquivo
 *   property on the DTO for the /nome endpoint to avoid exposing unrelated fields.
 *
 * - We intentionally do not call ArquivoDto.fromEntity(...) here because the application
 *   service returns simple values rather than the domain entity. Mapping is performed
 *   directly to avoid unnecessary entity exposure in the controller layer.
 *
 * - Simple error handling is provided:
 *   - 404 when ArquivoService signals not found
 *   - 400 for invalid input (IllegalArgumentException)
 *   - 500 for other unexpected errors
 *
 * TODO: (REVIEW) Consider centralized exception handling with @ControllerAdvice to unify
 *       error responses across controllers and remove duplicated try/catch blocks.
 */
@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * GET /api/arquivos/{id}/codigo
     *
     * @param id identifier of the Arquivo resource
     * @return ArquivoDto containing the legacy-styled codigo value
     */
    @GetMapping("/{id}/codigo")
    public ResponseEntity<ArquivoDto> getCodigoById(@PathVariable("id") Integer id) {
        try {
            // Delegate to application service which preserves legacy getter semantics.
            int codigo = arquivoService.getCodigoarquivoById(id);

            // Convert primitive to Integer for the DTO. This preserves the numeric value
            // while allowing DTO fields to be null in other flows if needed.
            ArquivoDto dto = new ArquivoDto(Integer.valueOf(codigo));
            return ResponseEntity.ok(dto);
        } catch (ArquivoService.ArquivoNotFoundException ex) {
            // Resource not found -> 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException ex) {
            // Bad request (e.g., null id) -> 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            // Unexpected errors -> 500
            // TODO: (REVIEW) Consider centralized exception handling with @ControllerAdvice
            // to unify error responses across controllers.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/arquivos/{id}/nome
     *
     * Delegates to ArquivoService#getNomearquivoById(id) and returns an ArquivoDto with
     * the legacy-styled 'nomearquivo' property populated. This preserves the legacy API
     * naming and behavior for clients expecting the exact property name.
     *
     * Notes / Decisions:
     * - We create a DTO with only the nomearquivo populated to avoid exposing other fields
     *   when only the name is requested.
     * - We preserve legacy naming by using ArquivoDto#setNomearquivo(...) which maps to the
     *   JSON property "nomearquivo" via annotations in the DTO.
     * - We intentionally maintain the same error handling strategy as the /codigo endpoint.
     *
     * TODO: (REVIEW) If multiple endpoints require similar error mapping, extract to @ControllerAdvice.
     */
    @GetMapping("/{id}/nome")
    public ResponseEntity<ArquivoDto> getNomeById(@PathVariable("id") Integer id) {
        try {
            String nome = arquivoService.getNomearquivoById(id);

            // Build DTO and populate only nomearquivo to preserve legacy response shape.
            ArquivoDto dto = new ArquivoDto();
            dto.setNomearquivo(nome);

            return ResponseEntity.ok(dto);
        } catch (ArquivoService.ArquivoNotFoundException ex) {
            // Resource not found -> 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException ex) {
            // Bad request (e.g., null id) -> 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            // Unexpected errors -> 500
            // TODO: (REVIEW) Consider centralized exception handling with @ControllerAdvice
            // to unify error responses across controllers.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
