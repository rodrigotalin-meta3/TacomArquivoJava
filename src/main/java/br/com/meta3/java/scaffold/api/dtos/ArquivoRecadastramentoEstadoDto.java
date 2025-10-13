package br.com.meta3.java.scaffold.api.dtos;

import br.com.meta3.java.scaffold.domain.entities.ArquivoRecadastramentoEstado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * API Data Transfer Object for ArquivoRecadastramentoEstado.
 *
 * Provides basic validation annotations and mapping helpers to/from the domain entity.
 *
 * Design notes:
 * - The legacy model initialized all fields to empty strings. The DTO mirrors that defaulting
 *   behaviour in its no-arg constructor to ease mapping and reduce null-handling in simple flows.
 * - 'codigo' in the domain entity was chosen as the @Id (natural key). We require it to be not blank
 *   in the DTO since the domain entity expects a non-empty identifier. If, in the future, a generated
 *   surrogate id is introduced, the validation groups should be adapted (e.g., allow null on create).
 *
 * TODO: (REVIEW) CNPJ format is not strictly validated here to avoid rejecting legacy formatted values.
 * If enforcing Brazilian CNPJ correctness is required, add a @Pattern(...) or a custom validator.
 */
public class ArquivoRecadastramentoEstadoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Validation group for create (POST) operations.
     * Kept for future use if different rules are needed between create/update.
     */
    public interface Create {
    }

    /**
     * Validation group for update (PUT/PATCH) operations.
     */
    public interface Update {
    }

    // Natural identifier (required). The domain entity marked this as @NotBlank.
    @NotBlank
    @Size(max = 50)
    private String codigo;

    @Size(max = 50)
    private String codigoSec;

    // Kept as String to preserve legacy formatting; length limited to 50.
    @Size(max = 50)
    private String dataMovimentacao;

    @Size(max = 10)
    private String anoBase;

    @Size(max = 255)
    private String nome;

    @Size(max = 20)
    private String cnpj;

    @Size(max = 100)
    private String bairro;

    public ArquivoRecadastramentoEstadoDto() {
        // Mirror legacy defaulting to empty strings to reduce null handling for simple clients.
        this.codigo = "";
        this.codigoSec = "";
        this.dataMovimentacao = "";
        this.anoBase = "";
        this.nome = "";
        this.cnpj = "";
        this.bairro = "";
    }

    public ArquivoRecadastramentoEstadoDto(String codigo,
                                           String codigoSec,
                                           String dataMovimentacao,
                                           String anoBase,
                                           String nome,
                                           String cnpj,
                                           String bairro) {
        this.codigo = codigo;
        this.codigoSec = codigoSec;
        this.dataMovimentacao = dataMovimentacao;
        this.anoBase = anoBase;
        this.nome = nome;
        this.cnpj = cnpj;
        this.bairro = bairro;
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

    /**
     * Map this DTO to the domain entity.
     *
     * Note: This performs a straightforward field copy. If in the future more complex mapping
     * (e.g., parsing dataMovimentacao into a date) is required, adapt this method or use a mapper.
     */
    public ArquivoRecadastramentoEstado toEntity() {
        ArquivoRecadastramentoEstado entity = new ArquivoRecadastramentoEstado();
        // TODO: (REVIEW) We copy fields directly; ensure callers set any additional identity semantics expected by the domain.
        entity.setCodigo(this.codigo != null ? this.codigo : "");
        entity.setCodigoSec(this.codigoSec != null ? this.codigoSec : "");
        entity.setDataMovimentacao(this.dataMovimentacao != null ? this.dataMovimentacao : "");
        entity.setAnoBase(this.anoBase != null ? this.anoBase : "");
        entity.setNome(this.nome != null ? this.nome : "");
        entity.setCnpj(this.cnpj != null ? this.cnpj : "");
        entity.setBairro(this.bairro != null ? this.bairro : "");
        return entity;
    }

    /**
     * Create a DTO from a domain entity.
     */
    public static ArquivoRecadastramentoEstadoDto fromEntity(ArquivoRecadastramentoEstado entity) {
        if (entity == null) {
            return null;
        }
        return new ArquivoRecadastramentoEstadoDto(
                entity.getCodigo(),
                entity.getCodigoSec(),
                entity.getDataMovimentacao(),
                entity.getAnoBase(),
                entity.getNome(),
                entity.getCnpj(),
                entity.getBairro()
        );
    }
}