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
 * - Adds additional legacy properties migrated from the domain entity so API responses can
 *   include them when needed:
 *     - quantidaderegistro, aptos, semdocumento, comcodigosetps, comerro (numeric counters)
 *     - anovigencia, codigoescola (string fields)
 *
 * - Uses boxed types (Integer) for numeric fields in the DTO to preserve nullability semantics.
 *
 * TODO: (REVIEW) If mapping responsibilities grow, consider moving conversion logic to a dedicated
 * mapper (e.g., ArquivoMapper) in the application layer to decouple API <-> domain mapping.
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
     */
    @JsonProperty("nomearquivo")
    private String nomearquivo;

    /**
     * Legacy numeric properties migrated from the domain entity.
     *
     * Stored as boxed Integer to preserve nullability semantics when mapping from the domain entity.
     * Annotated to guarantee JSON serialization uses the exact legacy names expected by clients.
     */
    @JsonProperty("quantidaderegistro")
    private Integer quantidaderegistro;

    @JsonProperty("aptos")
    private Integer aptos;

    @JsonProperty("semdocumento")
    private Integer semdocumento;

    @JsonProperty("comcodigosetps")
    private Integer comcodigosetps;

    @JsonProperty("comerro")
    private Integer comerro;

    /**
     * Legacy string properties.
     */
    @JsonProperty("anovigencia")
    private String anovigencia;

    @JsonProperty("codigoescola")
    private String codigoescola;

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

    //
    // New legacy properties' getters/setters
    //

    public Integer getQuantidaderegistro() {
        return this.quantidaderegistro;
    }

    public void setQuantidaderegistro(Integer quantidaderegistro) {
        this.quantidaderegistro = quantidaderegistro;
    }

    public Integer getAptos() {
        return this.aptos;
    }

    public void setAptos(Integer aptos) {
        this.aptos = aptos;
    }

    public Integer getSemdocumento() {
        return this.semdocumento;
    }

    public void setSemdocumento(Integer semdocumento) {
        this.semdocumento = semdocumento;
    }

    public Integer getComcodigosetps() {
        return this.comcodigosetps;
    }

    public void setComcodigosetps(Integer comcodigosetps) {
        this.comcodigosetps = comcodigosetps;
    }

    public Integer getComerro() {
        return this.comerro;
    }

    public void setComerro(Integer comerro) {
        this.comerro = comerro;
    }

    public String getAnovigencia() {
        return this.anovigencia;
    }

    public void setAnovigencia(String anovigencia) {
        this.anovigencia = anovigencia;
    }

    public String getCodigoescola() {
        return this.codigoescola;
    }

    public void setCodigoescola(String codigoescola) {
        this.codigoescola = codigoescola;
    }

    /**
     * Factory to create a DTO from the domain entity.
     *
     * Decision note:
     * - We call the conventional/camelCase boxed getters on the entity (when available) to
     *   preserve nullability semantics in the DTO mapping so that null in the entity is preserved
     *   as null in the DTO.
     *
     * - For legacy-named fields where the entity exposes only legacy primitive getters, prefer
     *   boxed equivalents when present on the entity to avoid losing null information.
     *
     * TODO: (REVIEW) If the legacy primitive behavior (defaulting to 0) must be preserved in API
     *       responses, switch to using arquivo.getQuantidaderegistro() (primitive) and adapt DTO
     *       semantics accordingly.
     */
    public static ArquivoDto fromEntity(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        // Map codigo using boxed getter to preserve nullability.
        ArquivoDto dto = new ArquivoDto(arquivo.getCodigoArquivo());
        // Map nomearquivo using conventional entity getter to preserve nullability.
        dto.setNomearquivo(arquivo.getNomeArquivo());

        // Map additional legacy properties.
        //
        // Decision: use conventional/boxed getters on the entity (e.g., getQuantidadeRegistro())
        // when available to preserve nullability. This avoids accidentally representing "not set"
        // as 0 in the API response.
        //
        // TODO: (REVIEW) If DB schema enforces non-null defaults and API must reflect legacy defaults,
        // adjust mapping to return primitive defaults instead.
        dto.setQuantidaderegistro(arquivo.getQuantidadeRegistro());
        dto.setAptos(arquivo.getAptosValue()); // boxed variant on entity
        dto.setSemdocumento(arquivo.getSemDocumento());
        dto.setComcodigosetps(arquivo.getComCodigoSetps());
        dto.setComerro(arquivo.getComErro());

        // String legacy fields - use conventional getters when available to preserve nullability.
        dto.setAnovigencia(arquivo.getAnoVigencia());
        dto.setCodigoescola(arquivo.getCodigoEscola());

        // TODO: (REVIEW) Consider using a mapper or constructor that accepts all fields
        // for immutability or validation if DTOs grow more complex.
        return dto;
    }
}
