package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Domain entity representing an Arquivo.
 *
 * This class preserves the legacy getter name and behavior for codigoarquivo:
 * public int getCodigoarquivo() { return this.codigoarquivo; }
 *
 * It is annotated as a JPA entity so it can be persisted using Spring Data JPA.
 */
@Entity
@Table(name = "arquivos")
public class Arquivo {

    // TODO: (REVIEW) Chose Long id with GenerationType.IDENTITY to represent a DB-generated primary key
    // TODO: (REVIEW) Using Long to follow common JPA practices and to allow null before persistence
    // GenerationType.IDENTITY keeps the responsibility of id generation on the database.
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    // TODO: (REVIEW) Mapping Legacy codigoarquivo as primitive int to preserve legacy getter behavior
    // // Preserve legacy accessor semantics:
    // // public int getCodigoarquivo() { return this.codigoarquivo; }
    @Column(name = "codigoarquivo", nullable = false)
    private int codigoarquivo;

    // Default constructor required by JPA
    public Arquivo() {
    }

    // Convenience constructor
    public Arquivo(int codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Legacy getter preserved exactly as in the legacy code to maintain compatibility.
    public int getCodigoarquivo() {
        return this.codigoarquivo;
    }

    public void setCodigoarquivo(int codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", codigoarquivo=" + codigoarquivo +
                '}';
    }
}