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
 * - The primary key is annotated with @Id. A generation strategy is provided
 *   (IDENTITY) to allow typical auto-increment behavior in relational DBs.
 *
 * TODO: (REVIEW) If the DB schema uses a different generation approach or column name,
 * adjust @GeneratedValue and @Column annotations accordingly.
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
     *
     * TODO: (REVIEW) Consider adding length, nullable and unique constraints to @Column if the
     * database schema requires them (e.g., @Column(name = "nomearquivo", nullable = false, length = 255)).
     */
    @Column(name = "nomearquivo")
    private String nomearquivo;

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
     * Legacy setter overload preserved to maintain backward/source compatibility with
     * older code that used the primitive signature: public void setCodigoarquivo(int).
     *
     * Decision notes:
     * - Accepts a primitive int and assigns it to the Integer backing field.
     * - Uses Integer.valueOf(...) to box the primitive; avoids assigning null and thus
     *   prevents potential NullPointerExceptions in legacy flows expecting a primitive.
     * - Keeping this overload ensures compiled clients calling setCodigoarquivo(int)
     *   continue to work without requiring changes.
     *
     * TODO: (REVIEW) Consider deprecating this primitive-based setter in favor of the
     * boxed setter (Integer) if nullability semantics are to be introduced in the future.
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
     * Note:
     * - We intentionally do not provide a primitive overload (not applicable for String).
     * - Validation (e.g., trimming, max length) should be applied at service/DTO level if needed.
     */
    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    // TODO: (REVIEW) Consider adding equals/hashCode and toString implementations if entities
    // will be used in collections or logs. For now, keeping the entity minimal to match migration scope.
}