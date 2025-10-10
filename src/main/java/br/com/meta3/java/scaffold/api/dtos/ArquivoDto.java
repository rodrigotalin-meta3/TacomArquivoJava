package br.com.meta3.java.scaffold.api.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;

/**
 * DTO representing Arquivo payloads for API responses/requests.
 *
 * Notes:
 * - Keeps the legacy field name "codigoarquivo" (all lowercase after 'codigo') to preserve
 *   compatibility with clients expecting that exact property name.
 * - Adds legacy-styled "nomearquivo" property to preserve compatibility with clients that
 *   expect that field in API responses.
 * - Uses Integer to represent the codigo value, allowing null to express "not set" in contrast
 *   with legacy primitive int getter semantics.
 *
 * TODO: (REVIEW) We currently reference the domain entity in the factory method below.
 *       If tighter decoupling is desired, consider moving mapping logic to a mapper class
 *       in the application layer (e.g., ArquivoMapper) to avoid API <-> domain coupling.
 */
public class ArquivoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Legacy-styled property name preserved for API compatibility.
     * Annotated with @JsonProperty to guarantee JSON serialization uses the exact name.
     */
    @JsonProperty("codigoarquivo")
    private Integer codigoarquivo;

    /**
     * Legacy-styled property name preserved for API compatibility (nomearquivo).
     * Annotated to guarantee JSON serialization uses the exact name expected by legacy clients.
     *
     * Decision:
     * - Stored as String and mapped from the domain entity via getNomeArquivo() to preserve
     *   nullability semantics (i.e., null in the entity maps to null in the DTO).
     */
    @JsonProperty("nomearquivo")
    private String nomearquivo;

    public ArquivoDto() {
        // Default constructor for frameworks (Jackson, etc.)
    }

    public ArquivoDto(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    /**
     * Convenience constructor including nomearquivo.
     *
     * TODO: (REVIEW) Consider removing this constructor if we prefer construction via builder
     * or factory methods to centralize mapping logic.
     */
    public ArquivoDto(Integer codigoarquivo, String nomearquivo) {
        this.codigoarquivo = codigoarquivo;
        this.nomearquivo = nomearquivo;
    }

    /**
     * Legacy-styled getter kept to match legacy naming conventions.
     * JavaBean property name will be "codigoarquivo".
     */
    public Integer getCodigoarquivo() {
        return this.codigoarquivo;
    }

    /**
     * Legacy-styled setter kept to match legacy naming conventions.
     */
    public void setCodigoarquivo(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    /**
     * Legacy-styled getter for 'nomearquivo' kept to preserve legacy naming conventions.
     *
     * Returns the raw backing String (may be null if not set).
     */
    public String getNomearquivo() {
        return this.nomearquivo;
    }

    /**
     * Legacy-styled setter for 'nomearquivo' kept to preserve legacy naming conventions.
     */
    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    /**
     * Factory to create a DTO from the domain entity.
     *
     * Decision note:
     * - The domain entity exposes two getters: a legacy primitive getCodigoarquivo() and a
     *   conventional getCodigoArquivo() that returns Integer. We call getCodigoArquivo()
     *   here to preserve nullability semantics in the DTO mapping (so that null in the entity
     *   is preserved as null in the DTO).
     *
     * - For the nomearquivo field we call getNomeArquivo() (conventional camelCase) on the
     *   entity to preserve nullability semantics as well.
     *
     * TODO: (REVIEW) If the legacy primitive behavior (defaulting to 0) must be preserved in API
     *       responses, switch to using arquivo.getCodigoarquivo() and adapt DTO type to int or
     *       handle conversion accordingly.
     */
    public static ArquivoDto fromEntity(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        // Map codigo using boxed getter to preserve nullability.
        ArquivoDto dto = new ArquivoDto(arquivo.getCodigoArquivo());
        // Map nomearquivo using conventional entity getter to preserve nullability.
        dto.setNomearquivo(arquivo.getNomeArquivo());

        // TODO: (REVIEW) Consider using a mapper or constructor that accepts both fields
        // for immutability or validation if DTOs grow more complex.
        return dto;
    }
}
