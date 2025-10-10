package br.com.meta3.java.scaffold.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests to verify legacy getter/setter behavior for Escola entity's codigoescola field.
 *
 * Goal:
 * - Ensure that when the backing field is null the legacy-styled getter getCodigoescola() returns null.
 * - Ensure that after setting the value via the legacy setter setCodigoescola(String),
 *   the legacy getter returns the assigned value.
 *
 * Decision notes:
 * - We intentionally test only the legacy-styled getter/setter pair (getCodigoescola/setCodigoescola)
 *   to preserve backward compatibility with legacy callers that depend on these exact method names.
 * - If in the future a conventional camelCase getter (e.g., getCodigoEscola) is introduced,
 *   extend this test to assert parity between both getters.
 *
 * TODO: (REVIEW) If normalization/validation is added to the setter in the future (e.g., trimming,
 *       max-length enforcement), add tests covering those behaviors as well.
 */
public class EscolaCodigoGetterTest {

    @Test
    public void testCodigoescolaGetterReturnsNullWhenBackingFieldIsNullAndReturnsValueAfterSetting() {
        Escola escola = new Escola();

        // Initial state: backing field is null -> legacy getter must return null
        assertNull(escola.getCodigoescola(), "Legacy getter getCodigoescola() should return null when backing field is null");

        // Use the legacy setter to assign a value and verify the legacy getter reflects it
        escola.setCodigoescola("ESC123");

        assertEquals("ESC123", escola.getCodigoescola(), "Legacy getter should return the value set via legacy setter");
    }

    // TODO: (REVIEW) Consider adding tests for edge cases:
    //  - Setting an empty string
    //  - Setting a very long code (validate DB column length if applicable)
    //  - Null assignment behavior if a boxed/overloaded setter is later introduced
}