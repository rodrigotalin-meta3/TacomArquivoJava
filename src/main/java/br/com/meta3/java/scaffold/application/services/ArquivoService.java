package br.com.meta3.java.scaffold.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.meta3.java.scaffold.api.dtos.CodigoArquivoDto;
import br.com.meta3.java.scaffold.domain.entities.Arquivo;
import br.com.meta3.java.scaffold.domain.repositories.ArquivoRepository;

import jakarta.validation.Valid;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    /**
     * Persist a CodigoArquivoDto by mapping its codigoEscola to the Arquivo entity and saving it.
     * Returns a CodigoArquivoDto populated with the persisted entity's codigoEscola value.
     *
     * Validation:
     *  - dto must not be null
     *  - dto.codigoEscola must not be null or blank
     *
     * Mapping decisions:
     *  - The legacy code used a setter named setCodigoescola(...) (lowercase 'e'). To remain compatible
     *    with existing entity implementations (which may use either codigoEscola or codigoescola naming),
     *    this method uses reflection to set the value on the entity. This avoids compile-time coupling
     *    to a specific setter/getter name and preserves compatibility with both legacy and modern naming.
     *
     *  - The returned DTO is populated using reflection as well to read back the saved value from the entity,
     *    ensuring we return the actual persisted value.
     */
    @Transactional
    public CodigoArquivoDto saveCodigoArquivo(@Valid CodigoArquivoDto dto) {
        Objects.requireNonNull(dto, "CodigoArquivoDto must not be null");

        String codigoEscola = null;
        try {
            // Try to read codigoEscola from DTO via conventional getter; if it doesn't exist this will throw.
            // We assume CódigoArquivoDto follows JavaBean conventions (getCodigoEscola).
            codigoEscola = (String) CodigoArquivoDto.class.getMethod("getCodigoEscola").invoke(dto);
        } catch (Exception e) {
            // If reflection fails, fallback to null and validation below will catch missing value.
            // TODO: (REVIEW) DTO getter access fallback - unable to invoke getCodigoEscola via reflection
        }

        if (codigoEscola == null || codigoEscola.trim().isEmpty()) {
            throw new IllegalArgumentException("codigoEscola must not be null or blank");
        }

        Arquivo arquivo = new Arquivo();

        // TODO: (REVIEW) Using reflection to set codigoEscola on Arquivo entity to support legacy method names
        // Reason: legacy entity had setCodigoescola(...) while newer conventions could use setCodigoEscola(...).
        // Using reflection keeps compatibility with both without compile-time dependency on a specific method name.
        setCodigoEscolaOnEntity(arquivo, codigoEscola);

        Arquivo saved = arquivoRepository.save(arquivo);

        // Read back persisted value to return in DTO
        String persistedCodigoEscola = readCodigoEscolaFromEntity(saved).orElse(codigoEscola);

        // Build return DTO - prefer existing DTO setter if available via reflection
        try {
            Method setter = CodigoArquivoDto.class.getMethod("setCodigoEscola", String.class);
            setter.invoke(dto, persistedCodigoEscola);
            return dto;
        } catch (Exception e) {
            // TODO: (REVIEW) Falling back to constructing a new CodigoArquivoDto and setting value reflectively
            try {
                CodigoArquivoDto newDto = CodigoArquivoDto.class.getDeclaredConstructor().newInstance();
                try {
                    Method setter = CodigoArquivoDto.class.getMethod("setCodigoEscola", String.class);
                    setter.invoke(newDto, persistedCodigoEscola);
                    return newDto;
                } catch (Exception ex) {
                    // If setter not available, try to set field directly
                    try {
                        Field f = CodigoArquivoDto.class.getDeclaredField("codigoEscola");
                        f.setAccessible(true);
                        f.set(newDto, persistedCodigoEscola);
                        return newDto;
                    } catch (Exception exc) {
                        throw new RuntimeException("Unable to populate CodigoArquivoDto with persisted codigoEscola", exc);
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException("Unable to instantiate CodigoArquivoDto to return persisted data", ex);
            }
        }
    }

    /**
     * Attempts to set the codigoEscola value on the entity using several strategies:
     *  1) Invoke setter "setCodigoEscola"
     *  2) Invoke setter "setCodigoescola" (legacy)
     *  3) Set field "codigoEscola"
     *  4) Set field "codigoescola"
     *
     * The order prefers modern naming but supports legacy naming. All accesses are done via reflection
     * to avoid compile-time dependency on a specific method/field name.
     */
    private void setCodigoEscolaOnEntity(Arquivo arquivo, String codigoEscola) {
        // Try setters first
        try {
            Method m = Arquivo.class.getMethod("setCodigoEscola", String.class);
            m.invoke(arquivo, codigoEscola);
            return;
        } catch (Exception ignored) {
            // continue to next attempt
        }

        try {
            // TODO: (REVIEW) Using legacy setter name setCodigoescola to maintain backward compatibility with legacy code
            Method m = Arquivo.class.getMethod("setCodigoescola", String.class);
            m.invoke(arquivo, codigoEscola);
            return;
        } catch (Exception ignored) {
            // continue to field access
        }

        try {
            Field f = Arquivo.class.getDeclaredField("codigoEscola");
            f.setAccessible(true);
            f.set(arquivo, codigoEscola);
            return;
        } catch (Exception ignored) {
            // continue
        }

        try {
            Field f = Arquivo.class.getDeclaredField("codigoescola");
            f.setAccessible(true);
            f.set(arquivo, codigoEscola);
            return;
        } catch (Exception e) {
            throw new RuntimeException("Failed to set codigoEscola on Arquivo entity via reflection", e);
        }
    }

    /**
     * Attempts to read codigoEscola from the given entity using several strategies:
     *  1) Invoke getter "getCodigoEscola"
     *  2) Invoke getter "getCodigoescola"
     *  3) Read field "codigoEscola"
     *  4) Read field "codigoescola"
     */
    private Optional<String> readCodigoEscolaFromEntity(Arquivo arquivo) {
        try {
            Method m = Arquivo.class.getMethod("getCodigoEscola");
            Object val = m.invoke(arquivo);
            return Optional.ofNullable(val != null ? val.toString() : null);
        } catch (Exception ignored) {
        }

        try {
            Method m = Arquivo.class.getMethod("getCodigoescola");
            Object val = m.invoke(arquivo);
            return Optional.ofNullable(val != null ? val.toString() : null);
        } catch (Exception ignored) {
        }

        try {
            Field f = Arquivo.class.getDeclaredField("codigoEscola");
            f.setAccessible(true);
            Object val = f.get(arquivo);
            return Optional.ofNullable(val != null ? val.toString() : null);
        } catch (Exception ignored) {
        }

        try {
            Field f = Arquivo.class.getDeclaredField("codigoescola");
            f.setAccessible(true);
            Object val = f.get(arquivo);
            return Optional.ofNullable(val != null ? val.toString() : null);
        } catch (Exception ignored) {
        }

        return Optional.empty();
    }
}