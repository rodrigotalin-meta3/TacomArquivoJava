package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 * JPA entity representing an Arquivo.
 *
 * Notes:
 * - The legacy code used a getter named getCodigoarquivo() (lowercase 'a' after codigo).
 *   To preserve backward compatibility, we provide that exact method name and also
 *   the conventional getCodigoArquivo().
 *
 * - This migration adds additional legacy properties that existed in the original
 *   aluno.Arquivo class: quantidaderegistro, aptos, semdocumento, comcodigosetps, comerro,
 *   anovigencia, codigoescola. Fields are mapped to columns with the same legacy names.
 *
 * - Numeric legacy fields are stored as boxed Integer to allow nullability in the modern
 *   domain model but legacy-styled primitive getters are provided (returning 0 when the
 *   backing Integer is null) to preserve original behavior of legacy consumers.
 *
 * TODO: (REVIEW) If DB schema requires non-null defaults or constraints, add nullable/length
 * attributes to @Column annotations and consider database migration scripts.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    // Using IDENTITY as a sensible default for numeric primary keys.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigoarquivo")
    private Integer codigoarquivo;

    /**
     * New field migrated from legacy code: nomearquivo
     *
     * Decision notes:
     * - Preserve legacy column name "nomearquivo" via @Column to match existing DB schema.
     * - Use String (boxed) type to allow storing null values if the database column permits it.
     * - Provide both legacy-styled getter getNomearquivo() and conventional getNomeArquivo()
     *   to maintain backward compatibility for older callers while allowing modern code to
     *   use conventional naming.
     */
    @Column(name = "nomearquivo")
    private String nomearquivo;

    /**
     * Legacy numeric counters migrated from aluno.Arquivo.
     *
     * Stored as boxed Integer to support nullability in the domain model while providing
     * legacy primitive getters that return 0 when the backing field is null. This preserves
     * the historical behavior of returning primitive defaults.
     *
     * Column names are kept identical to legacy field names to ease migration and DB mapping.
     */
    @Column(name = "quantidaderegistro")
    private Integer quantidaderegistro;

    @Column(name = "aptos")
    private Integer aptos;

    @Column(name = "semdocumento")
    private Integer semdocumento;

    @Column(name = "comcodigosetps")
    private Integer comcodigosetps;

    @Column(name = "comerro")
    private Integer comerro;

    /**
     * Legacy string fields. Initialize to empty string to mirror original legacy defaults
     * where applicable (original aluno.Arquivo used "" for these fields).
     *
     * Decision: initialize to empty string to avoid NPEs for callers that previously relied
     * on empty-string defaults. If the database allows NULL and nullability semantics are
     * preferred, remove the initializer.
     */
    @Column(name = "anovigencia")
    private String anovigencia = "";

    @Column(name = "codigoescola")
    private String codigoescola = "";

    public Arquivo() {
        // Default constructor required by JPA
    }

    public Arquivo(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    /**
     * Legacy getter kept exactly as in legacy code to preserve binary/source compatibility.
     * Legacy: getCodigoarquivo()
     */
    public int getCodigoarquivo() {
        // Preserve legacy behavior: return primitive int as in original snippet.
        // Handle potential null by returning 0 when codigoarquivo is null to avoid NPE.
        // This mirrors a common legacy pattern where primitive types were expected.
        return this.codigoarquivo != null ? this.codigoarquivo : 0;
    }

    /**
     * Conventional camelCase getter to follow Java naming conventions and modern code style.
     */
    public Integer getCodigoArquivo() {
        return this.codigoarquivo;
    }

    /**
     * Conventional setter for the field.
     */
    public void setCodigoarquivo(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    /**
     * Legacy-styled setter overload preserved to maintain backward/source compatibility with
     * older code that used the primitive signature: public void setCodigoarquivo(int).
     *
     * Decision notes:
     * - Accepts a primitive int and assigns it to the Integer backing field.
     * - Uses Integer.valueOf(...) to box the primitive; avoids assigning null and thus
     *   prevents potential NullPointerExceptions in legacy flows expecting a primitive.
     * - Keeping this overload ensures compiled clients calling setCodigoarquivo(int)
     *   continue to work without requiring changes.
     */
    public void setCodigoarquivo(int codigoarquivo) {
        this.codigoarquivo = Integer.valueOf(codigoarquivo);
    }

    /**
     * Legacy-styled getter for 'nomearquivo' kept to preserve legacy source/binary compatibility.
     *
     * Returns the raw backing String (may be null if not set).
     */
    public String getNomearquivo() {
        return this.nomearquivo;
    }

    /**
     * Conventional camelCase getter for 'nomearquivo' following modern naming conventions.
     *
     * Provides the same value as getNomearquivo() but with conventional method name.
     */
    public String getNomeArquivo() {
        return this.nomearquivo;
    }

    /**
     * Conventional setter for the 'nomearquivo' field.
     *
     * Preserves legacy/source compatibility by using the same name as the field.
     *
     * Legacy origin note:
     * - This setter preserves the legacy API/ABI by keeping the exact method name
     *   expected by older callers: setNomearquivo(String).
     * - The method assigns the backing field directly without normalization or validation
     *   to match historical behavior. Validation/normalization should be applied at the
     *   service or DTO layer if needed.
     *
     * @param nomearquivo legacy field value
     */
    public void setNomearquivo(String nomearquivo) {
        // TODO: (REVIEW) If normalization/validation is required in the future (e.g., trimming,
        // max-length enforcement), apply it in the application/service layer or introduce
        // a dedicated setter that performs validation and deprecate this legacy setter.
        this.nomearquivo = nomearquivo;
    }

    //
    // New legacy numeric properties: quantidaderegistro, aptos, semdocumento, comcodigosetps, comerro
    //
    // For each numeric field we provide:
    //  - legacy-styled primitive getter that returns 0 when backing Integer is null
    //  - conventional boxed getter (camelCase)
    //  - boxed setter (Integer) and primitive overload setter (int) to mirror legacy API
    //

    // quantidaderegistro
    public int getQuantidaderegistro() {
        return this.quantidaderegistro != null ? this.quantidaderegistro : 0;
    }

    // Conventional getter
    public Integer getQuantidadeRegistro() {
        return this.quantidaderegistro;
    }

    // Boxed setter
    public void setQuantidaderegistro(Integer quantidaderegistro) {
        this.quantidaderegistro = quantidaderegistro;
    }

    // Primitive overload setter (legacy)
    public void setQuantidaderegistro(int quantidaderegistro) {
        this.quantidaderegistro = Integer.valueOf(quantidaderegistro);
    }

    // aptos
    public int getAptos() {
        return this.aptos != null ? this.aptos : 0;
    }

    public Integer getAptosValue() {
        return this.aptos;
    }

    public void setAptos(Integer aptos) {
        this.aptos = aptos;
    }

    public void setAptos(int aptos) {
        this.aptos = Integer.valueOf(aptos);
    }

    // semdocumento
    public int getSemdocumento() {
        return this.semdocumento != null ? this.semdocumento : 0;
    }

    public Integer getSemDocumento() {
        return this.semdocumento;
    }

    public void setSemdocumento(Integer semdocumento) {
        this.semdocumento = semdocumento;
    }

    public void setSemdocumento(int semdocumento) {
        this.semdocumento = Integer.valueOf(semdocumento);
    }

    // comcodigosetps
    public int getComcodigosetps() {
        return this.comcodigosetps != null ? this.comcodigosetps : 0;
    }

    public Integer getComCodigoSetps() {
        return this.comcodigosetps;
    }

    public void setComcodigosetps(Integer comcodigosetps) {
        this.comcodigosetps = comcodigosetps;
    }

    public void setComcodigosetps(int comcodigosetps) {
        this.comcodigosetps = Integer.valueOf(comcodigosetps);
    }

    // comerro
    public int getComerro() {
        return this.comerro != null ? this.comerro : 0;
    }

    public Integer getComErro() {
        return this.comerro;
    }

    public void setComerro(Integer comerro) {
        this.comerro = comerro;
    }

    public void setComerro(int comerro) {
        this.comerro = Integer.valueOf(comerro);
    }

    //
    // New legacy string properties: anovigencia, codigoescola
    //
    // Provide legacy-styled getters/setters and conventional camelCase variants.
    //

    // anovigencia (legacy)
    public String getAnovigencia() {
        return this.anovigencia;
    }

    // conventional
    public String getAnoVigencia() {
        return this.anovigencia;
    }

    public void setAnovigencia(String anovigencia) {
        this.anovigencia = anovigencia;
    }

    public void setAnoVigencia(String anoVigencia) {
        this.anovigencia = anoVigencia;
    }

    // codigoescola (legacy)
    public String getCodigoescola() {
        return this.codigoescola;
    }

    // conventional
    public String getCodigoEscola() {
        return this.codigoescola;
    }

    public void setCodigoescola(String codigoescola) {
        this.codigoescola = codigoescola;
    }

    public void setCodigoEscola(String codigoEscola) {
        this.codigoescola = codigoEscola;
    }

    // TODO: (REVIEW) Consider adding equals/hashCode and toString implementations if entities
    // will be used in collections or logs. For now, keeping the entity minimal to match migration scope.
}