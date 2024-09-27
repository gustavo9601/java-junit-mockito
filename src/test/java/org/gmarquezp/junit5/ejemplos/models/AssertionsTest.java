package org.gmarquezp.junit5.ejemplos.models;


import org.gmarquezp.junit5.ejemplos.services.ISmartPhoneService;
import org.gmarquezp.junit5.ejemplos.services.SmartPhoneServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionsTest {

    @DisplayName("Test de conteo de SmartPhones")
    @Test
    void count(){
        ISmartPhoneService smartPhoneService = new SmartPhoneServiceImpl();

        Integer count = smartPhoneService.count();
        // Verifica que el valor no sea nulo
        assertNotNull(count);
        // Verifica que el valor sea mayor a 0
        assertTrue(count > 0);
        // Verifica que el valor sea igual a 3
        assertEquals(3, count);
    }

}
