package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.CodigoArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Updates the codigoarquivo field of an existing Arquivo entity.
     * The method preserves transactional behavior to ensure consistency.
     *
     * @param id  the id of the Arquivo to update
     * @param dto the DTO containing the new codigoArquivo value
     * @return the saved Arquivo entity with the updated value
     * @throws IllegalArgumentException   if dto is null
     * @throws EntityNotFoundException    if no Arquivo with the given id exists
     */
    @Transactional
    public Arquivo updateCodigoArquivo(Long id, CodigoArquivoDto dto) {
        Objects.requireNonNull(dto, "CodigoArquivoDto must not be null");

        Arquivo arquivo = arquivoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arquivo not found with id " + id));

        // TODO: (REVIEW) Using Arquivo.setCodigoarquivo with DTO value in place of legacy raw setter
        arquivo.setCodigoarquivo(dto.getCodigoArquivo());

        return arquivoRepository.save(arquivo);
    }
}