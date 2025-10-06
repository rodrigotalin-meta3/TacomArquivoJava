package br.com.meta3.java.scaffold.api.dtos;

import br.com.meta3.java.scaffold.domain.entities.Arquivo;

/**
 * DTO to carry the codigoarquivo value in API responses.
 * Keeps the API layer separate from the domain entity by exposing only the necessary data.
 */
public class CodigoArquivoDto {

    // Using Integer instead of primitive int to allow representing absence (null) if needed
    // TODO: (REVIEW) Using Integer instead of primitive int for nullable responses from domain
    // CodigoArquivoDto.fromEntity(arquivo)
    private final Integer codigoarquivo;

    public CodigoArquivoDto(Integer codigoarquivo) {
        this.codigoarquivo = codigoarquivo;
    }

    /**
     * Simple getter following JavaBean convention so frameworks (like Jackson) can serialize it.
     */
    public Integer getCodigoarquivo() {
        return this.codigoarquivo;
    }

    /**
     * Factory method to create DTO from domain entity.
     * Keeps mapping logic centralized and avoids leaking domain types into controllers.
     */
    // TODO: (REVIEW) Mapping Arquivo to CodigoArquivoDto to keep API layer separate from domain entity
    // CodigoArquivoDto.fromEntity(arquivo)
    public static CodigoArquivoDto fromEntity(Arquivo arquivo) {
        if (arquivo == null) {
            return null;
        }
        // Arquivo#getCodigoarquivo() originally returned an int in legacy code; preserve the value here.
        return new CodigoArquivoDto(arquivo.getCodigoarquivo());
    }

    @Override
    public String toString() {
        return "CodigoArquivoDto{codigoarquivo=" + codigoarquivo + '}';
    }
}