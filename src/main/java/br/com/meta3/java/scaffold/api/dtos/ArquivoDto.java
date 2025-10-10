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
 * - Uses Integer to represent the value, allowing null to express "not set" in contrast
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

    public ArquivoDto() {
        // Default constructor for frameworks (Jackson, etc.)
    }

    public ArquivoDto(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
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
     * Factory to create a DTO from the domain entity.
     *
     * Decision note:
     * - The domain entity exposes two getters: a legacy primitive getCodigoarquivo() and a
     *   conventional getCodigoArquivo() that returns Integer. We call getCodigoArquivo()
     *   here to preserve nullability semantics in the DTO mapping (so that null in the entity
     *   is preserved as null in the DTO).
     *
     * TODO: (REVIEW) If the legacy primitive behavior (defaulting to 0) must be preserved in API
     *       responses, switch to using arquivo.getCodigoarquivo() and adapt DTO type to int or
     *       handle conversion accordingly.
     */
    public static ArquivoDto fromEntity(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        return new ArquivoDto(arquivo.getCodigoArquivo());
    }
}