package br.com.meta3.java.scaffold.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for codigoArquivo used in API requests/responses.
 * Field uses Jakarta Validation annotations to ensure valid input.
 */
public class CodigoArquivoDto {

    @NotNull(message = "codigoArquivo must not be null")
    @Min(value = 1, message = "codigoArquivo must be at least 1")
    private Integer codigoArquivo;

    public CodigoArquivoDto() {
    }

    public CodigoArquivoDto(Integer codigoArquivo) {
        this.codigoArquivo = codigoArquivo;
    }

    // TODO: (REVIEW) Renamed legacy setter setCodigoarquivo to setCodigoArquivo to follow JavaBean conventions
    // The assignment below preserves the original behavior from the legacy code (setting the backing field).
    // Keeping the standard JavaBean name ensures Spring's data binding and validation work correctly.
    public void setCodigoArquivo(Integer codigoArquivo) {
        this.codigoArquivo = codigoArquivo;
    }

    // TODO: (REVIEW) Added getter getCodigoArquivo to expose the field for serializers and validation frameworks.
    public Integer getCodigoArquivo() {
        return this.codigoArquivo;
    }

    @Override
    public String toString() {
        return "CodigoArquivoDto{codigoArquivo=" + codigoArquivo + '}';
    }
}