package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.api.dtos.CodigoArquivoDto;
import br.com.meta3.java.scaffold.application.services.ArquivoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing endpoints related to Arquivo resources.
 */
@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    /**
     * GET /arquivos/{id}/codigo
     * Returns the codigoarquivo for the given Arquivo id wrapped in CodigoArquivoDto.
     *
     * Notes on migration decisions:
     * - We call ArquivoService to obtain the legacy value produced by the legacy getter getCodigoarquivo().
     * - If the service returns null or the resource is not found, we respond with 404 Not Found.
     */

    @GetMapping("/{id}/codigo")
    public ResponseEntity<CodigoArquivoDto> getCodigoArquivo(@PathVariable Long id) {
        // TODO: (REVIEW) Calling ArquivoService#getCodigoArquivoById to fetch codigoarquivo from service.
        // If ArquivoService has a different API (e.g. returns the Arquivo entity), adapt this call to
        // arquivoService.findById(id) and map entity.getCodigoarquivo() into CodigoArquivoDto.
        Integer codigo = null;
        try {
            // Here we assume ArquivoService exposes a method `getCodigoArquivoById(Long id)` returning Integer.
            // This is a minimal-adapter decision to preserve legacy getCodigoarquivo() functionality via service.
            codigo = arquivoService.getCodigoArquivoById(id);
        } catch (NoSuchMethodError | AbstractMethodError ex) {
            // TODO: (REVIEW) Fallback handling: if ArquivoService doesn't expose getCodigoArquivoById,
            // update this controller to call the correct service method (e.g. findById) and extract the value.
            // Keeping this catch to make the decision explicit during review/migration.
            throw ex;
        }

        if (codigo == null) {
            return ResponseEntity.notFound().build();
        }

        // TODO: (REVIEW) Mapping legacy int codigoarquivo into CodigoArquivoDto.
        // Legacy getter: public int getCodigoarquivo() { return this.codigoarquivo; }
        // We preserve that behavior by wrapping the returned value into the DTO.
        CodigoArquivoDto dto = new CodigoArquivoDto(codigo);
        return ResponseEntity.ok(dto);
    }
}