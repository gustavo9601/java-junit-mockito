package org.gmarquezp.junit5.ejemplos.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled // Se deshabilita la clase
public class DisabledTest {
    @Test
    @Disabled("Se deshabilita ya que es una prueba")
    void name() {
        assertEquals(1, 0);
    }

    @Test
    void name2() {
        assertEquals("", "");
    }
}
