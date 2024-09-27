package org.gmarquezp.junit5.ejemplos.models;
import org.gmarquezp.junit5.ejemplos.services.ISmartPhoneService;
import org.gmarquezp.junit5.ejemplos.services.SmartPhoneServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CicloVidaTest {

    ISmartPhoneService smartPhoneService;

    @BeforeEach
    void setUp() {
        this.smartPhoneService = new SmartPhoneServiceImpl();
        System.out.println("Iniciando pruebas antes de cada método");
    }

    @AfterEach
    void tearDown() {
        // Suele limpiarce rcusos, eliminado recursos de manejarse para no alterar otros test
        System.out.println("Finalizando pruebas después de cada método");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Iniciando pruebas una vez al comienzo");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando pruebas una vez al final");
    }

    @Test
    void count() {
        Integer count = this.smartPhoneService.count();
        assertNotNull(count);
    }
}
