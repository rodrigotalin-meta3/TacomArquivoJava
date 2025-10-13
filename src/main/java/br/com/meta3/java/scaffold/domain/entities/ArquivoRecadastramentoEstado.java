package br.com.meta3.java.scaffold.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * JPA entity migrated from legacy aluno.ArquivoRecadastramentoEstado.
 *
 * Mapping decisions:
 * - Kept original field types as String to preserve legacy behaviour and avoid surprising
 *   conversions. If a stricter type (e.g. LocalDate for dataMovimentacao) is desired later,
 *   a migration and mapping strategy should be introduced.
 * - Chose 'codigo' as the primary key (@Id). The legacy class did not declare a distinct id;
 *   'codigo' appears to be a natural identifier in the legacy model. If the domain requires
 *   a surrogate numeric id, replace/add a @GeneratedValue field and adapt repositories/services.
 * - Added basic validation annotations (NotBlank / Size) to integrate with Jakarta Validation.
 *   These constraints are conservative and can be tightened according to business rules.
 *
 * TODO: (REVIEW) Consider:
 *  - Introducing a surrogate generated id (Integer) if 'codigo' is not unique or mutable.
 *  - Converting dataMovimentacao to a date/time type and normalizing persisted values.
 *  - Adding a CNPJ-specific pattern validator if CNPJ format should be enforced.
 */
@Entity
@Table(name = "arquivo_recadastramento_estado")
public class ArquivoRecadastramentoEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    // Natural identifier chosen from legacy model. Marked as not blank to avoid persisting
    // empty keys. If this is not suitable, migrate to a generated surrogate id.
    @Id
    @Column(name = "codigo", length = 50)
    @NotBlank
    @Size(max = 50)
    private String codigo;

    @Column(name = "codigo_sec", length = 50)
    @Size(max = 50)
    private String codigoSec;

    // Kept as String to preserve legacy values/formats. Consider switching to LocalDate with
    // proper parsing/formatting if a date semantics is required.
    @Column(name = "data_movimentacao", length = 50)
    @Size(max = 50)
    private String dataMovimentacao;

    @Column(name = "ano_base", length = 10)
    @Size(max = 10)
    private String anoBase;

    @Column(name = "nome", length = 255)
    @Size(max = 255)
    private String nome;

    // CNPJ stored as String. Format validation is not enforced here to avoid rejecting legacy
    // formatted values; add @Pattern if strict validation is required.
    @Column(name = "cnpj", length = 20)
    @Size(max = 20)
    private String cnpj;

    @Column(name = "bairro", length = 100)
    @Size(max = 100)
    private String bairro;

    /**
     * Default constructor required by JPA.
     *
     * Initialize fields with empty strings to mirror legacy defaulting behaviour where
     * fields were initialised to "" in the original POJO.
     */
    public ArquivoRecadastramentoEstado() {
        this.codigo = "";
        this.codigoSec = "";
        this.dataMovimentacao = "";
        this.anoBase = "";
        this.nome = "";
        this.cnpj = "";
        this.bairro = "";
    }

    // Getters and setters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoSec() {
        return codigoSec;
    }

    public void setCodigoSec(String codigoSec) {
        this.codigoSec = codigoSec;
    }

    public String getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(String dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getAnoBase() {
        return anoBase;
    }

    public void setAnoBase(String anoBase) {
        this.anoBase = anoBase;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    // TODO: (REVIEW) Consider implementing equals/hashCode based on the chosen identifier (codigo)
    // if entity comparisons by identity are needed in collections or tests.

    @Override
    public String toString() {
        return "ArquivoRecadastramentoEstado{" +
                "codigo='" + codigo + '\'' +
                ", codigoSec='" + codigoSec + '\'' +
                ", dataMovimentacao='" + dataMovimentacao + '\'' +
                ", anoBase='" + anoBase + '\'' +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", bairro='" + bairro + '\'' +
                '}';
    }
}