package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * Domain JPA entity migrated from legacy aluno.Arquivo.
 */
@Entity
@Table(name = "arquivo")
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    // Primary key mapped from legacy 'codigoarquivo'
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_arquivo")
    private Integer codigoarquivo;

    // File name - in legacy code this was a simple String.
    // Adding validation: must not be blank and limited in length.
    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_arquivo", length = 255)
    private String nomearquivo;

    // Numeric counters from legacy code. Using Integer to allow nulls prior to persistence.
    // Validation: must be zero or positive when provided.
    @NotNull
    @Min(0)
    @Column(name = "quantidade_registro")
    private Integer quantidaderegistro;

    @NotNull
    @Min(0)
    @Column(name = "aptos")
    private Integer aptos;

    @NotNull
    @Min(0)
    @Column(name = "sem_documento")
    private Integer semdocumento;

    @NotNull
    @Min(0)
    @Column(name = "com_codigo_setps")
    private Integer comcodigosetps;

    @NotNull
    @Min(0)
    @Column(name = "com_erro")
    private Integer comerro;

    // Year of validity - kept as String to preserve legacy behaviour (was String).
    @Size(max = 10)
    @Column(name = "ano_vigencia", length = 10)
    private String anovigencia = "";

    // School code - kept as String.
    @Size(max = 50)
    @Column(name = "codigo_escola", length = 50)
    private String codigoescola = "";

    /**
     * Default constructor required by JPA.
     */
    public Arquivo() {
    }

    // Getters and setters

    public Integer getCodigoarquivo() {
        return this.codigoarquivo;
    }

    public void setCodigoarquivo(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    public String getNomearquivo() {
        return this.nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

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

    // TODO: (REVIEW) Chose Integer for numeric fields instead of primitive int to allow null
    // before persistence and to better integrate with JPA and validation flows.
    // If the domain requires primitives (no nulls), convert back to int and provide default 0.
}