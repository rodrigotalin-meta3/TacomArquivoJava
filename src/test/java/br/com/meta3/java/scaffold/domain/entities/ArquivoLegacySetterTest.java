package br.com.meta3.java.scaffold.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests to verify legacy setter/getter behavior for Arquivo entity.
 *
 * Goal:
 * - Validate that the primitive-based legacy setter setCodigoarquivo(int) correctly assigns
 *   the backing Integer field and that both legacy primitive getter getCodigoarquivo()
 *   and conventional boxed getter getCodigoArquivo() return expected values.
 *
 * Decision notes:
 * - We intentionally test initial null-state behavior (Entity constructed with no value)
 *   to ensure legacy getter getCodigoarquivo() returns 0 (primitive default behavior implemented
 *   in the entity) while getCodigoArquivo() returns null.
 * - Tests are simple, do not require Spring context; they exercise the entity in isolation.
 *
 * TODO: (REVIEW) If nullability semantics change in the future (e.g., remove primitive getter),
 *       adjust tests accordingly.
 */
public class ArquivoLegacySetterTest {

    @Test
    public void testPrimitiveSetterSetsValueAndGettersReturnExpectedResults() {
        Arquivo arquivo = new Arquivo();

        // Verify initial legacy behavior: backing Integer is null -> primitive getter returns 0,
        // boxed getter returns null.
        assertEquals(0, arquivo.getCodigoarquivo(), "Legacy primitive getter should return 0 when backing field is null");
        assertNull(arquivo.getCodigoArquivo(), "Boxed getter should return null when backing field is null");

        // Use the legacy primitive setter and verify both getters reflect the assigned value.
        arquivo.setCodigoarquivo(123);

        // Legacy primitive getter must return the exact primitive value set.
        assertEquals(123, arquivo.getCodigoarquivo(), "Legacy primitive getter should return the value set via primitive setter");

        // Conventional boxed getter should return the boxed Integer with the same numeric value.
        assertEquals(Integer.valueOf(123), arquivo.getCodigoArquivo(), "Boxed getter should return the boxed value set via primitive setter");
    }

    // TODO: (REVIEW) Consider adding more tests for edge cases (e.g., negative values, large integers)
    // and for the boxed setter setCodigoarquivo(Integer) if nullability behavior needs explicit coverage.
}