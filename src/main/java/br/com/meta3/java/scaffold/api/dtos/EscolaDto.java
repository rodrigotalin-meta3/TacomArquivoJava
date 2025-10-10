package br.com.meta3.java.scaffold.api.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.meta3.java.scaffold.domain.entities.Escola;

/**
 * DTO representing Escola payloads for API responses/requests.
 *
 * Notes:
 * - Keeps the legacy field name "codigoescola" (all lowercase after 'codigo') to preserve
 *   compatibility with clients expecting that exact property name.
 * - Stored as String to reflect the legacy entity getter getCodigoescola() which returns String.
 * - Provides legacy-styled getter/setter names so Jackson and other frameworks will produce
 *   the exact JSON property "codigoescola" expected by legacy clients.
 *
 * TODO: (REVIEW) If mapping responsibilities grow, consider moving conversion logic to a dedicated
 * mapper (e.g., EscolaMapper) in the application layer to decouple API <-> domain mapping.
 */
public class EscolaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Legacy-styled property name preserved for API compatibility.
     * Annotated with @JsonProperty to guarantee JSON serialization uses the exact name.
     */
    @JsonProperty("codigoescola")
    private String codigoescola;

    public EscolaDto() {
        // Default constructor for frameworks (Jackson, etc.)
    }

    public EscolaDto(String codigoescola) {
        this.codigoescola = codigoescola;
    }

    /**
     * Legacy-styled getter kept to match legacy naming conventions.
     * JavaBean property name will be "codigoescola".
     */
    public String getCodigoescola() {
        return this.codigoescola;
    }

    /**
     * Legacy-styled setter kept to match legacy naming conventions.
     */
    public void setCodigoescola(String codigoescola) {
        this.codigoescola = codigoescola;
    }

    /**
     * Factory to create a DTO from the domain entity.
     *
     * Decision note:
     * - We call the legacy-styled entity getter getCodigoescola() to preserve legacy semantics
     *   and nullability behavior across the mapping.
     *
     * - If tighter decoupling is desired, move this mapping to an application-layer mapper.
     */
    public static EscolaDto fromEntity(Escola escola) {
        if (escola == null) {
            return null;
        }
        EscolaDto dto = new EscolaDto(escola.getCodigoescola());
        return dto;
    }

    // TODO: (REVIEW) Consider adding validation annotations (e.g., @NotBlank) on DTO fields if API
    // contract requires stricter validation. Validation is currently left to the service layer to
    // preserve legacy behavior and avoid surprising clients.