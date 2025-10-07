package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Domain entity representing an Arquivo.
 *
 * This class preserves a legacy-compatible setter name (setCodigoarquivo) while also
 * providing a convention-friendly camelCase setter (setCodigoArquivo) that delegates to the legacy one.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Legacy code used a field/setter named "codigoarquivo" (all lowercase).
     * We keep the field name matching the legacy database column name for compatibility,
     * but provide camelCase accessors for a cleaner API.
     */
    @Column(name = "codigoarquivo")
    private Integer codigoarquivo;

    public Arquivo() {
    }

    public Arquivo(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Legacy-compatible setter (preserves the exact name/signature from legacy code).
     *
     * NOTE: This method intentionally keeps the legacy lowercase naming to avoid breaking
     * any existing reflective or serialized usages that expect this exact method name.
     */
    // TODO: (REVIEW) Preserve legacy lowercase setter for backward compatibility
    // TODO: (REVIEW) setCodigoarquivo(int codigoarquivo)
    public void setCodigoarquivo(int codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    /**
     * Convention-friendly camelCase setter that delegates to the legacy setter to preserve behavior.
     *
     * By delegating, we ensure any centralized logic (if later added) inside the legacy setter
     * continues to be executed for both setter variants.
     */
    // TODO: (REVIEW) Provide camelCase setter delegating to legacy setter to offer conventional API
    // TODO: (REVIEW) setCodigoarquivo(codigoArquivo)
    public void setCodigoArquivo(int codigoArquivo) {
        // Delegate to legacy-compatible setter to keep single point of assignment/behavior.
        setCodigoarquivo(codigoArquivo);
    }

    /**
     * Getter using camelCase naming for convenience.
     */
    public Integer getCodigoArquivo() {
        return this.codigoarquivo;
    }

    /**
     * Legacy-style getter for completeness in case legacy consumers expect it.
     */
    public Integer getCodigoarquivo() {
        return this.codigoarquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(id, arquivo.id) &&
                Objects.equals(codigoarquivo, arquivo.codigoarquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoarquivo);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", codigoarquivo=" + codigoarquivo +
                '}';
    }
}