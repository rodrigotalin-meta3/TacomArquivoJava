package br.com.meta3.java.scaffold.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests to verify legacy/conventional getter behavior for the 'nomearquivo' field.
 *
 * Goal:
 * - Ensure that when the backing field is null both legacy-styled getter getNomearquivo()
 *   and conventional camelCase getter getNomeArquivo() return null.
 * - Ensure that after setting the value via the legacy setter setNomearquivo(String),
 *   both getters return the same assigned value.
 *
 * Decision notes:
 * - We use the legacy setter setNomearquivo(String) to mirror how older code would have
 *   mutated the entity; this verifies backwards compatibility of the migration.
 * - Both getters intentionally return the same backing field; the test asserts this contract.
 *
 * TODO: (REVIEW) If future requirements introduce normalization (trimming, validations)
 *       in setters or getters, add tests covering those behaviors. Consider adding tests
 *       for persistence-related behaviors if JPA mapping influences runtime values.
 */
public class ArquivoNomearquivoGetterTest {

    @Test
    public void testNomearquivoGettersReturnNullWhenBackingFieldIsNullAndReturnValueAfterSetting() {
        Arquivo arquivo = new Arquivo();

        // Initial state: nomearquivo backing field is null -> both getters must return null.
        assertNull(arquivo.getNomearquivo(), "Legacy getter getNomearquivo() should return null when backing field is null");
        assertNull(arquivo.getNomeArquivo(), "Conventional getter getNomeArquivo() should return null when backing field is null");

        // Use the legacy setter to assign a value and verify both getters reflect it.
        arquivo.setNomearquivo("meu_arquivo.txt");

        assertEquals("meu_arquivo.txt", arquivo.getNomearquivo(), "Legacy getter should return the value set via legacy setter");
        assertEquals("meu_arquivo.txt", arquivo.getNomeArquivo(), "Conventional getter should return the same value as legacy getter after setting");
    }

    // TODO: (REVIEW) Consider additional tests for edge cases:
    //  - Setting an empty string
    //  - Setting a very long name (validate DB column length if applicable)
    //  - Null assignment behavior if a boxed setter is later introduced
}