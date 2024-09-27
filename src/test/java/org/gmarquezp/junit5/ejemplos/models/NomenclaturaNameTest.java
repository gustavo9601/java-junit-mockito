package org.gmarquezp.junit5.ejemplos.models;

import org.gmarquezp.junit5.ejemplos.services.ISmartPhoneService;
import org.gmarquezp.junit5.ejemplos.services.SmartPhoneServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NomenclaturaNameTest - Smartphone CRUD test")
public class NomenclaturaNameTest {

    @DisplayName("findOne check type of RAM")
    @Test
    void test1() {
        ISmartPhoneService smartPhoneService = new SmartPhoneServiceImpl();
        SmartPhone smartPhone = smartPhoneService.findOne(1L);

        assertEquals(smartPhone.getRam().getType(), "DDR4");
    }

    @DisplayName("findOne check capacity of battery")
    @Test
    void test2() {
        ISmartPhoneService smartPhoneService = new SmartPhoneServiceImpl();
        SmartPhone smartPhone = smartPhoneService.findOne(1L);

        assertEquals(smartPhone.getBattery().getCapacity(), 4500.0);
    }
}
