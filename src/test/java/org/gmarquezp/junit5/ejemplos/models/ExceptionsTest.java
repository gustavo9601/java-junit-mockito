package org.gmarquezp.junit5.ejemplos.models;

import org.gmarquezp.junit5.ejemplos.services.ISmartPhoneService;
import org.gmarquezp.junit5.ejemplos.services.SmartPhoneServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {
    @Test
    void findOneIlegalArgumentExeptionTest() {
        ISmartPhoneService smartPhoneService = new SmartPhoneServiceImpl();
        // Verifica que el método lanza una excepción
        assertThrows(IllegalArgumentException.class, () -> smartPhoneService.findOne(null));

    }
}
