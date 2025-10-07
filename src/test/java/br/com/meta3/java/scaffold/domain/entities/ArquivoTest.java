package br.com.meta3.java.scaffold.domain.entities;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArquivoTest {

    @Test
    public void testGetCodigoescolaReturnsSetValue() throws Exception {
        String expected = "ABC123";

        Class<Arquivo> clazz = Arquivo.class;
        Object arquivoInstance;

        // TODO: (REVIEW) Prefer no-arg constructor instantiation; using reflection to handle cases
        // where the legacy entity may not expose public constructors. This keeps the test robust
        // against different possible implementations of Arquivo (no-arg, arg-constructor, private ctor).
        try {
            Constructor<Arquivo> noArgCtor = clazz.getDeclaredConstructor();
            noArgCtor.setAccessible(true);
            arquivoInstance = noArgCtor.newInstance();
        } catch (NoSuchMethodException ex) {
            // TODO: (REVIEW) Falling back to a single-String-arg constructor if present to set codigoescola at construction time.
            Constructor<?>[] ctors = clazz.getDeclaredConstructors();
            Constructor<?> stringCtor = null;
            for (Constructor<?> c : ctors) {
                if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == String.class) {
                    stringCtor = c;
                    break;
                }
            }
            if (stringCtor != null) {
                stringCtor.setAccessible(true);
                arquivoInstance = stringCtor.newInstance(expected);
            } else {
                // If no suitable constructor, try to instantiate via Unsafe-like means is overkill here;
                // rethrow to fail the test and surface the missing accessible constructor.
                throw ex;
            }
        }

        // TODO: (REVIEW) Attempt to use a public setter first; if not available, use reflection to set the private field.
        try {
            Method setter = clazz.getMethod("setCodigoescola", String.class);
            setter.invoke(arquivoInstance, expected);
        } catch (NoSuchMethodException e) {
            // Fallback: set the field via reflection (handles private/protected fields)
            Field field = null;
            Class<?> current = clazz;
            while (current != null) {
                try {
                    field = current.getDeclaredField("codigoescola");
                    break;
                } catch (NoSuchFieldException nsf) {
                    current = current.getSuperclass();
                }
            }
            if (field == null) {
                throw new NoSuchFieldException("Field 'codigoescola' not found in class hierarchy of Arquivo");
            }
            field.setAccessible(true);
            field.set(arquivoInstance, expected);
        }

        // Invoke the legacy getter to assert behavior is preserved.
        Method getter = clazz.getMethod("getCodigoescola");
        Object actual = getter.invoke(arquivoInstance);

        assertEquals(expected, actual);
    }
}