file content
package br.com.meta3.java.scaffold.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests verifying legacy getters/setters for all migrated fields.
 *
 * Goals:
 * - Validate primitive getters return 0 when backing Integer is null (legacy behavior).
 * - Validate primitive setter assigns value and boxed getter returns boxed value.
 * - Validate boxed setter assigns value and primitive getter returns primitive value.
 * - Validate other numeric legacy fields (quantidaderegistro, aptos, semdocumento,
 *   comcodigosetps, comerro) preserve values and nullability.
 * - Validate legacy string fields (anovigencia, codigoescola) preserve defaults and setter behavior.
 *
 * Decision notes / complex decisions:
 * - The entity initializes anovigencia and codigoescola to empty strings ("") in the migrated entity.
 *   Tests assert the empty-string default instead of null to reflect that deliberate migration decision.
 * - Numeric fields are stored as boxed Integer in the domain entity but legacy primitive getters return 0
 *   when backing field is null. Tests assert both boxed and primitive behaviors to ensure parity with legacy code.
 *
 * TODO: (REVIEW) If entity defaults change (e.g., strings become nullable), update tests accordingly.
 */
public class ArquivoLegacyFieldsTest {

    @Test
    public void testCodigoarquivoPrimitiveAndBoxedBehavior() {
        Arquivo arquivo = new Arquivo();

        // Initial state: backing Integer is null -> primitive getter returns 0, boxed getter returns null
        assertEquals(0, arquivo.getCodigoarquivo(), "Legacy primitive getter should return 0 when backing Integer is null");
        assertNull(arquivo.getCodigoArquivo(), "Conventional boxed getter should return null when backing Integer is null");

        // Legacy primitive setter should set the backing Integer; primitive getter returns set value,
        // boxed getter returns boxed value.
        arquivo.setCodigoarquivo(123);
        assertEquals(123, arquivo.getCodigoarquivo(), "Legacy primitive getter should return the value set via primitive setter");
        assertEquals(Integer.valueOf(123), arquivo.getCodigoArquivo(), "Boxed getter should return the boxed value set via primitive setter");

        // Boxed setter should also work and be observable via both getters.
        arquivo.setCodigoarquivo(Integer.valueOf(456));
        assertEquals(456, arquivo.getCodigoarquivo(), "Primitive getter should reflect the boxed setter value");
        assertEquals(Integer.valueOf(456), arquivo.getCodigoArquivo(), "Boxed getter should reflect the boxed setter value");
    }

    @Test
    public void testQuantidaderegistroBehavior() {
        // quantidaderegistro: initial null -> primitive getter 0, boxed getter null;
        // both primitive and boxed setters should update the value and be visible via both getters.
        Arquivo arquivo = new Arquivo();

        assertEquals(0, arquivo.getQuantidaderegistro(), "Primitive getter for quantidaderegistro should return 0 when null");
        assertNull(arquivo.getQuantidadeRegistro(), "Boxed getter for quantidaderegistro should return null when null");

        arquivo.setQuantidaderegistro(10);
        assertEquals(10, arquivo.getQuantidaderegistro(), "Primitive getter should return value set via primitive setter");
        assertEquals(Integer.valueOf(10), arquivo.getQuantidadeRegistro(), "Boxed getter should return boxed value set via primitive setter");

        arquivo.setQuantidaderegistro(Integer.valueOf(20));
        assertEquals(20, arquivo.getQuantidaderegistro(), "Primitive getter should return value set via boxed setter");
        assertEquals(Integer.valueOf(20), arquivo.getQuantidadeRegistro(), "Boxed getter should return boxed value set via boxed setter");
    }

    @Test
    public void testAptosBehavior() {
        Arquivo arquivo = new Arquivo();

        assertEquals(0, arquivo.getAptos(), "Primitive getter for aptos should return 0 when null");
        assertNull(arquivo.getAptosValue(), "Boxed getter for aptos should return null when null");

        arquivo.setAptos(5);
        assertEquals(5, arquivo.getAptos(), "Primitive getter should return value set via primitive setter");
        assertEquals(Integer.valueOf(5), arquivo.getAptosValue(), "Boxed getter should return boxed value set via primitive setter");

        arquivo.setAptos(Integer.valueOf(7));
        assertEquals(7, arquivo.getAptos(), "Primitive getter should return value set via boxed setter");
        assertEquals(Integer.valueOf(7), arquivo.getAptosValue(), "Boxed getter should return boxed value set via boxed setter");
    }

    @Test
    public void testSemdocumentoBehavior() {
        Arquivo arquivo = new Arquivo();

        assertEquals(0, arquivo.getSemdocumento(), "Primitive getter for semdocumento should return 0 when null");
        assertNull(arquivo.getSemDocumento(), "Boxed getter for semdocumento should return null when null");

        arquivo.setSemdocumento(3);
        assertEquals(3, arquivo.getSemdocumento(), "Primitive getter should return value set via primitive setter");
        assertEquals(Integer.valueOf(3), arquivo.getSemDocumento(), "Boxed getter should return boxed value set via primitive setter");

        arquivo.setSemdocumento(Integer.valueOf(4));
        assertEquals(4, arquivo.getSemdocumento(), "Primitive getter should return value set via boxed setter");
        assertEquals(Integer.valueOf(4), arquivo.getSemDocumento(), "Boxed getter should return boxed value set via boxed setter");
    }

    @Test
    public void testComcodigosetpsBehavior() {
        Arquivo arquivo = new Arquivo();

        assertEquals(0, arquivo.getComcodigosetps(), "Primitive getter for comcodigosetps should return 0 when null");
        assertNull(arquivo.getComCodigoSetps(), "Boxed getter for comcodigosetps should return null when null");

        arquivo.setComcodigosetps(2);
        assertEquals(2, arquivo.getComcodigosetps(), "Primitive getter should return value set via primitive setter");
        assertEquals(Integer.valueOf(2), arquivo.getComCodigoSetps(), "Boxed getter should return boxed value set via primitive setter");

        arquivo.setComcodigosetps(Integer.valueOf(9));
        assertEquals(9, arquivo.getComcodigosetps(), "Primitive getter should return value set via boxed setter");
        assertEquals(Integer.valueOf(9), arquivo.getComCodigoSetps(), "Boxed getter should return boxed value set via boxed setter");
    }

    @Test
    public void testComerroBehavior() {
        Arquivo arquivo = new Arquivo();

        assertEquals(0, arquivo.getComerro(), "Primitive getter for comerro should return 0 when null");
        assertNull(arquivo.getComErro(), "Boxed getter for comerro should return null when null");

        arquivo.setComerro(1);
        assertEquals(1, arquivo.getComerro(), "Primitive getter should return value set via primitive setter");
        assertEquals(Integer.valueOf(1), arquivo.getComErro(), "Boxed getter should return boxed value set via primitive setter");

        arquivo.setComerro(Integer.valueOf(11));
        assertEquals(11, arquivo.getComerro(), "Primitive getter should return value set via boxed setter");
        assertEquals(Integer.valueOf(11), arquivo.getComErro(), "Boxed getter should return boxed value set via boxed setter");
    }

    @Test
    public void testAnovigenciaAndCodigoescolaDefaultsAndSetters() {
        Arquivo arquivo = new Arquivo();

        // IMPORTANT: migrated entity intentionally initializes these fields to empty strings.
        // Tests reflect that migration decision by asserting empty-string defaults.
        assertEquals("", arquivo.getAnovigencia(), "anovigencia should default to empty string per migrated entity");
        assertEquals("", arquivo.getCodigoescola(), "codigoescola should default to empty string per migrated entity");

        // Legacy setters
        arquivo.setAnovigencia("2025");
        arquivo.setCodigoescola("ESC999");

        assertEquals("2025", arquivo.getAnovigencia(), "Legacy getter should return value set via legacy setter");
        assertEquals("ESC999", arquivo.getCodigoescola(), "Legacy getter should return value set via legacy setter");

        // Conventional setters (camelCase) should reflect the same backing field
        arquivo.setAnoVigencia("2030");
        arquivo.setCodigoEscola("ESC000");

        assertEquals("2030", arquivo.getAnovigencia(), "Legacy getter should reflect value set via conventional setter");
        assertEquals("ESC000", arquivo.getCodigoescola(), "Legacy getter should reflect value set via conventional setter");
    }

    @Test
    public void testNomearquivoGettersAndSettersPreserveNullabilityAndValue() {
        Arquivo arquivo = new Arquivo();

        // nomearquivo backing field is not initialized in migrated entity -> should be null initially
        assertNull(arquivo.getNomearquivo(), "Legacy getter getNomearquivo() should return null when backing field is null");
        assertNull(arquivo.getNomeArquivo(), "Conventional getter getNomeArquivo() should return null when backing field is null");

        arquivo.setNomearquivo("arquivo_teste.txt");
        assertEquals("arquivo_teste.txt", arquivo.getNomearquivo(), "Legacy getter should return value set via legacy setter");
        assertEquals("arquivo_teste.txt", arquivo.getNomeArquivo(), "Conventional getter should return same value as legacy getter");
    }
}