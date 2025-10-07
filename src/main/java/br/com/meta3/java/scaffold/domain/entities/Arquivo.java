package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * Domain entity representing an Arquivo.
 * Minimal fields are provided to preserve backwards compatibility while
 * introducing the new camelCase codigoEscola field mapped to the existing DB column.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: (REVIEW) Added codigoEscola (camelCase) to replace legacy codigoescola field and mapped to DB column 'codigoescola'
    // TODO: (REVIEW) Original legacy setter was setCodigoescola(String codigoescola). We keep compatibility by providing
    // TODO: (REVIEW) a legacy-named setter that delegates to the new camelCase setter.
    // // Legacy intent preserved:
    // setCodigoEscola(codigoescola);

    /**
     * Stores the school code. Column name kept as 'codigoescola' to match legacy DB schema.
     * Jakarta Validation annotations added to enforce non-blank values at the boundary.
     */
    @Column(name = "codigoescola")
    @NotBlank(message = "codigoEscola must not be blank")
    @Size(max = 255, message = "codigoEscola must be at most 255 characters")
    private String codigoEscola;

    public Arquivo() {
    }

    public Arquivo(Long id, String codigoEscola) {
        this.id = id;
        this.codigoEscola = codigoEscola;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for codigoEscola following camelCase convention.
     */
    public String getCodigoEscola() {
        return codigoEscola;
    }

    /**
     * Setter for codigoEscola following project conventions.
     * Uses this.codigoEscola = codigoEscola to assign the value (preserves legacy behavior).
     */
    public void setCodigoEscola(String codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    // TODO: (REVIEW) Provide legacy-style setter to preserve compatibility with any code that used the old method name.
    // setCodigoEscola(codigoescola)
    /**
     * Legacy-named setter preserved to maintain backward compatibility with existing callers.
     * Delegates to the new camelCase setter.
     */
    public void setCodigoescola(String codigoescola) {
        // Delegate to the canonical camelCase setter to keep a single assignment point.
        setCodigoEscola(codigoescola);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(id, arquivo.id) &&
               Objects.equals(codigoEscola, arquivo.codigoEscola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoEscola);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
               "id=" + id +
               ", codigoEscola='" + codigoEscola + '\'' +
               '}';
    }
}