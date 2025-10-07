package br.com.meta3.java.scaffold.api.dtos;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * DTO used by API layer to transfer codigoEscola for Arquivo domain entity.
 */
public class CodigoArquivoDto {

    // TODO: (REVIEW) Choosing a conservative max length (50) for codigoEscola to match likely legacy constraints
    // Validation: @Size(max = 50)
    @NotBlank(message = "codigoEscola must not be blank")
    @Size(max = 50, message = "codigoEscola must be at most 50 characters")
    private String codigoEscola;

    public CodigoArquivoDto() {
    }

    public CodigoArquivoDto(String codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    public String getCodigoEscola() {
        return codigoEscola;
    }

    public void setCodigoEscola(String codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    /**
     * Converts this DTO into a domain Arquivo entity.
     * Only sets codigoescola to preserve original legacy behavior; other Arquivo fields are left untouched.
     */
    public Arquivo toEntity() {
        Arquivo arquivo = new Arquivo();

        if (Objects.nonNull(this.codigoEscola)) {
            // TODO: (REVIEW) Mapping DTO.codigoEscola to domain Arquivo.setCodigoescola to preserve legacy setter naming
            // arquivo.setCodigoescola(this.codigoEscola)
            arquivo.setCodigoescola(this.codigoEscola);
        }

        return arquivo;
    }

    /**
     * Builds a DTO from a domain Arquivo entity.
     * If Arquivo is null, returns null.
     */
    public static CodigoArquivoDto fromEntity(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }

        CodigoArquivoDto dto = new CodigoArquivoDto();
        // TODO: (REVIEW) Assuming domain entity exposes getCodigoescola() getter to match legacy naming
        dto.setCodigoEscola(arquivo.getCodigoescola());
        return dto;
    }
}