package org.gmarquezp.junit5.ejemplos.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NestedAnidadosTest {

    /**
     * Se trata de inner class
     * - Permite agrupar test y darle un identacion
     * */

    @Nested // Se anida una clase
    @DisplayName("Test de la clase anidada")
    public class NestClass1{
        @Test
        void name() {
            assertEquals(1, 1);
        }

        @Test
        void name2() {
            assertEquals("", "");
        }
    }


    @DisplayName("Test de la clase base")
    @Test
    void name() {
        assertEquals(1, 1);
    }
}
