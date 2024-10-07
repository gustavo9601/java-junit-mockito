package org.gmarquezp.junit5.ejemplos.junit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class ConditionalAssumptionsTest {
    /**
     * Funcionara si una asersion se cumple continuara con las demas, si no se cumple se detiene
     * */

    @Test
    void name() {

        System.out.println("Variable COMPUTERNAME: " + System.getenv("COMPUTERNAME"));
        // Si pasa se ejecuta el siguiente test, en caso contrario la lambda se ejecutara
        assumeTrue("GMARQUEZ1".equals(System.getenv("COMPUTERNAME")), () -> "No se cumple la aserción");

        assertEquals(true, true);
    }
}
