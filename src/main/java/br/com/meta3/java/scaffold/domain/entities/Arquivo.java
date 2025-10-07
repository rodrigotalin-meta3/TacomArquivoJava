package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Domain entity representing an Arquivo (file).
 *
 * This class normalizes the filename property to camelCase (nomeArquivo) while preserving
 * backward compatibility with legacy code that used setNomearquivo(String).
 */
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Filename for the Arquivo.
     *
     * Note: The database column is kept as "nomearquivo" to preserve compatibility with any
     * existing schema that used the legacy field name.
     */
    // TODO: (REVIEW) Preserve legacy DB column name while using camelCase property in Java
    // NewSorter.sort(array)
    @Column(name = "nomearquivo")
    private String nomeArquivo;

    public Arquivo() {
    }

    public Arquivo(Long id, String nomeArquivo) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Modern getter for the filename property using camelCase naming.
     *
     * @return the filename
     */
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    /**
     * Modern setter for the filename property using camelCase naming.
     *
     * @param nomeArquivo the filename to set
     */
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    /**
     * Deprecated compatibility setter for legacy callers that used the old method name.
     * Delegates to setNomeArquivo to preserve a single source of assignment logic.
     */
    // TODO: (REVIEW) Provide deprecated compatibility setter delegating to the modern setter
    // NewSorter.sort(array)
    @Deprecated
    public void setNomearquivo(String nomearquivo) {
        // Delegate to the new camelCase setter to keep assignment logic centralized.
        setNomeArquivo(nomearquivo);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", nomeArquivo='" + nomeArquivo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arquivo arquivo = (Arquivo) o;

        if (id != null ? !id.equals(arquivo.id) : arquivo.id != null) return false;
        return nomeArquivo != null ? nomeArquivo.equals(arquivo.nomeArquivo) : arquivo.nomeArquivo == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nomeArquivo != null ? nomeArquivo.hashCode() : 0);
        return result;
    }
}