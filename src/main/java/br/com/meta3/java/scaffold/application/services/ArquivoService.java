package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for Arquivo domain operations.
 *
 * Responsibilities:
 * - Implement CRUD operations using the domain ArquivoRepository abstraction.
 * - Convert between Arquivo (domain entity) and ArquivoDto (API DTO).
 * - Perform validation using Jakarta Validator and DTO validation groups.
 * - Apply simple business rules (e.g., create must not include id, update must include id;
 *   numeric counters must be non-negative which is validated by annotations).
 *
 * Design decisions / notes:
 * - The service depends on the domain repository abstraction (ArquivoRepository) so the
 *   infrastructure layer can provide the actual persistence implementation (ArquivoJpaRepository).
 * - Validation is performed programmatically via jakarta.validation.Validator so the service can
 *   enforce Create/Update validation groups independently from controller-layer binding.
 * - For updates we perform a "partial update" (only non-null fields from the DTO overwrite
 *   the existing entity). This choice preserves existing values when the client does not
 *   provide a field. If a client needs to clear a field to null, additional semantics would
 *   be required (e.g., Optional-wrapping or explicit flags).
 * - Exceptions thrown are generic (ConstraintViolationException, NoSuchElementException,
 *   IllegalArgumentException). Controllers should translate them to appropriate HTTP responses.
 */
@Service
public class ArquivoService {

    private final ArquivoRepository repository;
    private final Validator validator;

    public ArquivoService(ArquivoRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    /**
     * Create a new Arquivo from the provided DTO.
     *
     * Validation: uses ArquivoDto.Create group. The DTO must not contain an id.
     *
     * @param dto data for creation
     * @return created Arquivo as DTO (with generated id)
     * @throws ConstraintViolationException when validation fails
     * @throws IllegalArgumentException when dto contains an id
     */
    @Transactional
    public ArquivoDto create(ArquivoDto dto) {
        // Validate DTO for create semantics
        Set<ConstraintViolation<ArquivoDto>> violations = validator.validate(dto, ArquivoDto.Create.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("ArquivoDto validation failed for create", violations);
        }

        if (dto.getCodigoarquivo() != null) {
            // TODO: (REVIEW) The legacy model used primitive int where id defaults to 0.
            // In our domain we treat id as generated and require null for create to avoid accidental updates.
            throw new IllegalArgumentException("codigoarquivo must be null when creating a new Arquivo");
        }

        Arquivo entity = dto.toEntity();
        // Ensure id is null before persisting
        entity.setCodigoarquivo(null);

        Arquivo saved = repository.save(entity);
        return ArquivoDto.fromEntity(saved);
    }

    /**
     * Update an existing Arquivo using values from the provided DTO.
     *
     * Validation: uses ArquivoDto.Update group. The DTO must include codigoarquivo.
     * Only non-null fields from the DTO are applied to the existing entity (partial update).
     *
     * @param dto data for update
     * @return updated Arquivo as DTO
     * @throws ConstraintViolationException when validation fails
     * @throws NoSuchElementException when the entity does not exist
     * @throws IllegalArgumentException when codigoarquivo is null
     */
    @Transactional
    public ArquivoDto update(ArquivoDto dto) {
        // Validate DTO for update semantics
        Set<ConstraintViolation<ArquivoDto>> violations = validator.validate(dto, ArquivoDto.Update.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("ArquivoDto validation failed for update", violations);
        }

        Integer id = dto.getCodigoarquivo();
        if (id == null) {
            throw new IllegalArgumentException("codigoarquivo must be provided when updating an Arquivo");
        }

        Optional<Arquivo> existingOpt = repository.findById(id);
        Arquivo existing = existingOpt.orElseThrow(() -> new NoSuchElementException("Arquivo not found with id: " + id));

        // Partial update: only non-null fields on the DTO overwrite the entity fields
        // TODO: (REVIEW) This approach preserves existing data when fields are omitted.
        // If clearing fields to null is required, the DTO must be adapted to signal that intent.
        if (dto.getNomearquivo() != null) {
            existing.setNomearquivo(dto.getNomearquivo());
        }
        if (dto.getQuantidaderegistro() != null) {
            existing.setQuantidaderegistro(dto.getQuantidaderegistro());
        }
        if (dto.getAptos() != null) {
            existing.setAptos(dto.getAptos());
        }
        if (dto.getSemdocumento() != null) {
            existing.setSemdocumento(dto.getSemdocumento());
        }
        if (dto.getComcodigosetps() != null) {
            existing.setComcodigosetps(dto.getComcodigosetps());
        }
        if (dto.getComerro() != null) {
            existing.setComerro(dto.getComerro());
        }
        if (dto.getAnovigencia() != null) {
            existing.setAnovigencia(dto.getAnovigencia());
        }
        if (dto.getCodigoescola() != null) {
            existing.setCodigoescola(dto.getCodigoescola());
        }

        Arquivo saved = repository.save(existing);
        return ArquivoDto.fromEntity(saved);
    }

    /**
     * Find an Arquivo by id.
     *
     * @param id primary key
     * @return Arquivo DTO if found
     * @throws NoSuchElementException when not found
     */
    @Transactional(readOnly = true)
    public ArquivoDto findById(Integer id) {
        return repository.findById(id)
                .map(ArquivoDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Arquivo not found with id: " + id));
    }

    /**
     * Return all Arquivo records.
     *
     * @return list of Arquivo DTOs
     */
    @Transactional(readOnly = true)
    public List<ArquivoDto> findAll() {
        return repository.findAll().stream()
                .map(ArquivoDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Delete an Arquivo by id.
     *
     * @param id primary key
     * @throws NoSuchElementException when not found
     */
    @Transactional
    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Arquivo not found with id: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Check existence by primary key.
     *
     * @param id primary key
     * @return true if exists
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Find Arquivo records by school code.
     *
     * @param codigoescola school code
     * @return list of Arquivo DTOs
     */
    @Transactional(readOnly = true)
    public List<ArquivoDto> findByCodigoescola(String codigoescola) {
        return repository.findByCodigoescola(codigoescola).stream()
                .map(ArquivoDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Find Arquivo records by year of validity.
     *
     * @param anovigencia year string
     * @return list of Arquivo DTOs
     */
    @Transactional(readOnly = true)
    public List<ArquivoDto> findByAnovigencia(String anovigencia) {
        return repository.findByAnovigencia(anovigencia).stream()
                .map(ArquivoDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Count total Arquivo records.
     *
     * @return total count
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}