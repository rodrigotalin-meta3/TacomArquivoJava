package br.com.meta3.java.scaffold.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.meta3.java.scaffold.application.services.EscolaService;
import br.com.meta3.java.scaffold.api.dtos.EscolaDto;

/**
 * REST controller exposing Escola-related endpoints.
 *
 * Exposes:
 *   GET /api/escolas/{id}/codigoescola
 *
 * Behavior:
 * - Delegates to EscolaService#getCodigoescolaById(Integer) which preserves legacy getter semantics.
 * - Returns an EscolaDto containing the legacy-styled property "codigoescola" to preserve API compatibility.
 *
 * Error mapping:
 * - EscolaService.EscolaNotFoundException -> 404 Not Found
 * - IllegalArgumentException -> 400 Bad Request
 * - Any other Exception -> 500 Internal Server Error
 *
 * Notes / Decisions:
 * - We create the DTO and populate only the codigoescola property to avoid exposing unrelated fields.
 * - Consider centralizing exception handling with @ControllerAdvice in the future to remove duplicated try/catch blocks.
 */
@RestController
@RequestMapping("/api/escolas")
public class EscolaController {

    private final EscolaService escolaService;

    public EscolaController(EscolaService escolaService) {
        this.escolaService = escolaService;
    }

    /**
     * GET /api/escolas/{id}/codigoescola
     *
     * @param id identifier of the Escola resource
     * @return EscolaDto containing the legacy-styled codigoescola value
     */
    @GetMapping("/{id}/codigoescola")
    public ResponseEntity<EscolaDto> getCodigoescolaById(@PathVariable("id") Integer id) {
        try {
            // Delegate to application service which preserves legacy getter semantics.
            String codigo = escolaService.getCodigoescolaById(id);

            // Build DTO preserving legacy property name.
            EscolaDto dto = new EscolaDto(codigo);
            return ResponseEntity.ok(dto);
        } catch (EscolaService.EscolaNotFoundException ex) {
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

    // TODO: (REVIEW) Add additional endpoints (e.g., POST/PUT) as needed and consider
    // moving mapping logic to a dedicated mapper if DTO construction grows more complex.
}
