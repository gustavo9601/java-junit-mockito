package org.gmarquezp.junit5.ejemplos.models;

import org.gmarquezp.junit5.ejemplos.services.ISmartPhoneService;
import org.gmarquezp.junit5.ejemplos.services.SmartPhoneServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertAllTest {
    /**
     * AssertAll -> Permite que todos los test se ejecuten aunque fallen, para que se muestren todos los errores
     * - Slavo alguna lance excepción no se ejecutarán los siguientes test
     * */
    @Test
    void assertAllTest() {
        ISmartPhoneService smartPhoneService = new SmartPhoneServiceImpl();

        assertAll("Titulo Comprobaciones SmartPhone",
                () -> assertEquals(smartPhoneService.count(), 3),
                () -> assertEquals(smartPhoneService.findByWifi(true).size(), 2),
                () -> assertEquals(smartPhoneService.findByWifi(true).size(), 5), // Falla
                () -> assertTrue(smartPhoneService.findOne(1L).getWifi()) // Falla
        );
    }
}
