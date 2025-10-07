package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "arquivo")
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Field name preserved from legacy code: 'nomearquivo'
    private String nomearquivo;

    public Arquivo() {
    }

    public Arquivo(Long id, String nomearquivo) {
        this.id = id;
        this.nomearquivo = nomearquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // TODO: (REVIEW) Added legacy-compatible getter 'getNomearquivo' and JavaBean-compliant getter 'getNomeArquivo'
    // NewSorter.sort(array)

    /**
     * JavaBean-compliant getter for 'nomearquivo'.
     * New code should use this method.
     */
    public String getNomeArquivo() {
        return this.nomearquivo;
    }

    /**
     * Legacy-compatible getter kept for backwards compatibility with older callers
     * that expect the non-JavaBean method name 'getNomearquivo'.
     */
    public String getNomearquivo() {
        return this.nomearquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomearquivo = nomeArquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arquivo)) return false;
        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(id, arquivo.id) &&
                Objects.equals(nomearquivo, arquivo.nomearquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomearquivo);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", nomearquivo='" + nomearquivo + '\'' +
                '}';
    }
}