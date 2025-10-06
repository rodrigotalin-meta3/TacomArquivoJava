package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/*
 // TODO: (REVIEW) Using primitive int for codigoarquivo to match legacy getter behavior
 int __migration_review_primitive_codigoarquivo = 0;
 
 Reason: The legacy code returned a primitive int (no nullability). To preserve the original
 API/behavior (getCodigoarquivo() returns an int and defaults to 0), we keep the field as a
 primitive int instead of Integer to avoid unexpected null/unboxing behavior.
*/

/*
 // TODO: (REVIEW) Using GenerationType.IDENTITY for @GeneratedValue to let DB auto-increment IDs
 int __migration_review_generation_strategy = 0;
 
 Reason: For a straightforward migration and compatibility with H2 and typical relational DBs,
 GenerationType.IDENTITY is chosen to let the database generate sequential ids. This keeps the
 entity simple and aligns with common JPA usage for numeric primary keys.
*/

@Entity
@Table(name = "arquivo")
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigoarquivo", nullable = false)
    private int codigoarquivo;

    // Default constructor required by JPA
    public Arquivo() {
    }

    // Convenience constructor
    public Arquivo(int codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    // Preserved legacy getter signature and behavior
    public int getCodigoarquivo(){
        return this.codigoarquivo;
    }

    public void setCodigoarquivo(int codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }
}