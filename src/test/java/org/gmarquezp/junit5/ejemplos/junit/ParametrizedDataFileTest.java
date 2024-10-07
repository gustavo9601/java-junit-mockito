package org.gmarquezp.junit5.ejemplos.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class ParametrizedDataFileTest {

    /**
     * Se le esepeifica por parametros que
     *
     * @ValueSource(strings = {"Hola", "Mundo"})
     * @CsvSource({"Hola, 5", "Mundo, 5"})
     * @CsvFileSource(resources = "/data.csv", delimiter = ';', encoding = "UTF-8", numLinesToSkip = 1)
     * @MethodSource("createData")
     * @EnumSource(value = Enum.class) // Se le pasa un enum
     */

    @ParameterizedTest
    @ValueSource(strings = {"Hola", "Mundo"})
    void testParametrized1(String word) {
        System.out.println("word = " + word);
        assertTrue(word.length() > 0);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1;Gus;28",
            "2;Pepe;30",
            "3;Lalo;25"}, delimiter = ';')
    void testParametrized2(String id, String name, Integer age) {
        System.out.println("id = " + id + ", name = " + name + ", age = " + age);
        assertNotNull(id);
        assertNotNull(name);
        assertNotNull(age);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void testParametrize3(Integer value) {
        System.out.println("Value: " + value);
        assertTrue(value > 0);
    }

    @ParameterizedTest
    @MethodSource("createData")
        // El metodo debe ser estatico
    void testParametrize4(String value) {
        System.out.println("Value: " + value);
        assertTrue(value.length() > 0);
    }

    static Stream<String> createData() {
        return Stream.of("Hola", "", "Hola Mundo", "");
    }


    enum RoleTest{
        ADMIN, USER, GUEST
    }

    @ParameterizedTest
    @EnumSource(value = RoleTest.class)
    void testParametrize5(RoleTest role) {
        System.out.println("Role: " + role);
        assertNotNull(role);
    }
}
