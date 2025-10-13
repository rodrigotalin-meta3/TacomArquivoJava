package br.com.meta3.java.scaffold.application.services;

import br.com.meta3.java.scaffold.api.dtos.ArquivoRecadastramentoEstadoDto;
import br.com.meta3.java.scaffold.domain.entities.ArquivoRecadastramentoEstado;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRecadastramentoEstadoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for ArquivoRecadastramentoEstado operations.
 *
 * Responsibilities:
 * - Validate DTOs using Jakarta Validator with Create/Update groups.
 * - Map between ArquivoRecadastramentoEstadoDto and ArquivoRecadastramentoEstado.
 * - Enforce create/update rules (e.g., uniqueness of natural key on create).
 * - Delegate persistence to the domain repository abstraction.
 *
 * Design notes / decisions:
 * - The legacy model used empty strings as defaults for many fields. The DTO mirrors that
 *   behaviour which means fields are frequently non-null but possibly empty. During update
 *   we treat empty strings as "explicit empty" (i.e., they overwrite existing values).
 *   If a future requirement is to differentiate "absent" from "empty" we should adapt the DTO
 *   to use Optional fields or null semantics for omitted values.
 * - 'codigo' is treated as the natural identifier and required by the DTO/domain. On create we
 *   validate that an entity with the same codigo does not already exist to avoid accidental overwrite.
 * - Validation is performed programmatically so the service controls which validation groups apply.
 */
@Service
public class ArquivoRecadastramentoEstadoService {

    private final ArquivoRecadastramentoEstadoRepository repository;
    private final Validator validator;

    public ArquivoRecadastramentoEstadoService(ArquivoRecadastramentoEstadoRepository repository,
                                               Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    /**
     * Create a new ArquivoRecadastramentoEstado from the provided DTO.
     *
     * Validation: uses ArquivoRecadastramentoEstadoDto.Create group.
     *
     * @param dto data to create
     * @return created entity as DTO
     * @throws ConstraintViolationException when validation fails
     * @throws IllegalArgumentException when an entity with the same codigo already exists
     */
    @Transactional
    public ArquivoRecadastramentoEstadoDto create(ArquivoRecadastramentoEstadoDto dto) {
        // Validate DTO for create semantics
        Set<ConstraintViolation<ArquivoRecadastramentoEstadoDto>> violations =
                validator.validate(dto, ArquivoRecadastramentoEstadoDto.Create.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("ArquivoRecadastramentoEstadoDto validation failed for create", violations);
        }

        String codigo = dto.getCodigo();
        // Defensive check: DTO declares codigo as @NotBlank; still ensure we don't attempt to create duplicates.
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("codigo must be provided when creating ArquivoRecadastramentoEstado");
        }

        if (repository.existsById(codigo)) {
            throw new IllegalArgumentException("ArquivoRecadastramentoEstado already exists with codigo: " + codigo);
        }

        ArquivoRecadastramentoEstado entity = dto.toEntity();
        // Persist and return mapped DTO
        ArquivoRecadastramentoEstado saved = repository.save(entity);
        return ArquivoRecadastramentoEstadoDto.fromEntity(saved);
    }

    /**
     * Update an existing ArquivoRecadastramentoEstado using values from the provided DTO.
     *
     * Validation: uses ArquivoRecadastramentoEstadoDto.Update group.
     *
     * Note: Because the DTO defaults many string fields to empty strings, we consider any value
     * present in the DTO (including empty string) as an explicit value to apply. If future
     * semantics require partial updates that omit fields, adapt the DTO to allow nulls for
     * omitted fields or use Optional wrappers.
     *
     * @param dto data to update
     * @return updated entity as DTO
     * @throws ConstraintViolationException when validation fails
     * @throws NoSuchElementException when the entity does not exist
     * @throws IllegalArgumentException when codigo is not provided
     */
    @Transactional
    public ArquivoRecadastramentoEstadoDto update(ArquivoRecadastramentoEstadoDto dto) {
        Set<ConstraintViolation<ArquivoRecadastramentoEstadoDto>> violations =
                validator.validate(dto, ArquivoRecadastramentoEstadoDto.Update.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("ArquivoRecadastramentoEstadoDto validation failed for update", violations);
        }

        String codigo = dto.getCodigo();
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("codigo must be provided when updating ArquivoRecadastramentoEstado");
        }

        ArquivoRecadastramentoEstado existing = repository.findById(codigo)
                .orElseThrow(() -> new NoSuchElementException("ArquivoRecadastramentoEstado not found with codigo: " + codigo));

        // Apply updates: DTO->Entity mapping.
        // TODO: (REVIEW) Current decision: treat DTO fields as authoritative for update (including empty strings).
        // If partial update semantics are desired (i.e., only non-null values overwrite), adapt DTO to use nulls for omitted fields.
        if (dto.getCodigoSec() != null) {
            existing.setCodigoSec(dto.getCodigoSec());
        }
        if (dto.getDataMovimentacao() != null) {
            existing.setDataMovimentacao(dto.getDataMovimentacao());
        }
        if (dto.getAnoBase() != null) {
            existing.setAnoBase(dto.getAnoBase());
        }
        if (dto.getNome() != null) {
            existing.setNome(dto.getNome());
        }
        if (dto.getCnpj() != null) {
            existing.setCnpj(dto.getCnpj());
        }
        if (dto.getBairro() != null) {
            existing.setBairro(dto.getBairro());
        }

        ArquivoRecadastramentoEstado saved = repository.save(existing);
        return ArquivoRecadastramentoEstadoDto.fromEntity(saved);
    }

    /**
     * Find an entity by codigo.
     *
     * @param codigo primary key
     * @return DTO representation
     * @throws NoSuchElementException when not found
     */
    @Transactional(readOnly = true)
    public ArquivoRecadastramentoEstadoDto findById(String codigo) {
        return repository.findById(codigo)
                .map(ArquivoRecadastramentoEstadoDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("ArquivoRecadastramentoEstado not found with codigo: " + codigo));
    }

    /**
     * Return all records.
     *
     * @return list of DTOs
     */
    @Transactional(readOnly = true)
    public List<ArquivoRecadastramentoEstadoDto> findAll() {
        return repository.findAll().stream()
                .map(ArquivoRecadastramentoEstadoDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Delete by codigo.
     *
     * @param codigo primary key
     * @throws NoSuchElementException when not found
     */
    @Transactional
    public void deleteById(String codigo) {
        if (!repository.existsById(codigo)) {
            throw new NoSuchElementException("ArquivoRecadastramentoEstado not found with codigo: " + codigo);
        }
        repository.deleteById(codigo);
    }

    /**
     * Exists check.
     *
     * @param codigo primary key
     * @return true if exists
     */
    @Transactional(readOnly = true)
    public boolean existsById(String codigo) {
        return repository.existsById(codigo);
    }

    /**
     * Find records by anoBase.
     *
     * @param anoBase year string
     * @return list of DTOs
     */
    @Transactional(readOnly = true)
    public List<ArquivoRecadastramentoEstadoDto> findByAnoBase(String anoBase) {
        return repository.findByAnoBase(anoBase).stream()
                .map(ArquivoRecadastramentoEstadoDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Find records by CNPJ.
     *
     * @param cnpj cnpj string
     * @return list of DTOs
     */
    @Transactional(readOnly = true)
    public List<ArquivoRecadastramentoEstadoDto> findByCnpj(String cnpj) {
        return repository.findByCnpj(cnpj).stream()
                .map(ArquivoRecadastramentoEstadoDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Find records by bairro.
     *
     * @param bairro neighbourhood string
     * @return list of DTOs
     */
    @Transactional(readOnly = true)
    public List<ArquivoRecadastramentoEstadoDto> findByBairro(String bairro) {
        return repository.findByBairro(bairro).stream()
                .map(ArquivoRecadastramentoEstadoDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Count total records.
     *
     * @return total count
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}