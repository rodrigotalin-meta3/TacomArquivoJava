package br.com.meta3.java.scaffold.api.dtos;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import java.util.Objects;

/**
 * DTO used to shape HTTP responses that expose the legacy arquivo codigoarquivo value.
 *
 * This DTO preserves the legacy primitive getter semantics by exposing an int campo named
 * codigoarquivo and a getter getCodigoarquivo() so that serialized JSON matches the original
 * domain expectation ({"codigoarquivo": <int>}).
 */
public final class ArquivoResponseDto {

    // Preserve legacy primitive type to mirror legacy getter behavior.
    private final int codigoarquivo;

    /**
     * Creates a response DTO with the given legacy codigoarquivo value.
     *
     * @param codigoarquivo legacy codigoarquivo value (primitive int)
     */
    public ArquivoResponseDto(int codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    /**
     * Legacy-style getter preserved to shape the JSON response payload exactly like the legacy model.
     *
     * @return primitive int codigoarquivo
     */
    public int getCodigoarquivo() {
        return this.codigoarquivo;
    }

    /**
     * Convenience factory to map a domain Arquivo entity into this response DTO.
     *
     * This helper centralizes the mapping and enforces a null-check on the incoming entity
     * so controllers/services can safely convert entities to DTOs.
     *
     * @param arquivo domain entity to map from
     * @return ArquivoResponseDto containing the legacy codigoarquivo value
     * @throws NullPointerException if arquivo is null
     */
    public static ArquivoResponseDto fromEntity(Arquivo arquivo) {
        // TODO: (REVIEW) Preserve legacy getter name in DTO mapping
        // arquivo.getCodigoarquivo()
        Objects.requireNonNull(arquivo, "arquivo must not be null");
        return new ArquivoResponseDto(arquivo.getCodigoarquivo());
    }

    // TODO: (REVIEW) Using immutable DTOs to avoid accidental mutation in API layer
    // ArquivoResponseDto is final and fields are final
}