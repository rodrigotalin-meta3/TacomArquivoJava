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
 *   the conventional camelCase getter getCodigoArquivo().
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

    // TODO: (REVIEW) Consider adding equals/hashCode and toString implementations if entities
    // will be used in collections or logs. For now, keeping the entity minimal to match migration scope.
}