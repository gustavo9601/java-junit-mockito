package org.gmarquezp.junit5.ejemplos.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionalTest {
    /**
     * Se ejecuta el test si se pasan ciertas condiciones
     */

    @EnabledOnOs(OS.WINDOWS) // Solo se ejcuta si esta en windows
    @Test
    void windowsTest() {
        assertEquals(1, 1);
    }


    @EnabledOnOs({OS.WINDOWS, OS.LINUX}) // Solo se ejcuta si esta en windows y linux
    @Test
    void windowsLinuxTest() {
        assertEquals(1, 1);
    }

    @EnabledOnJre(JRE.JAVA_8) // Solo se ejecuta si esta en java 8
    @Test
    void java8Test() {
        assertEquals(1, 1);
    }

    @EnabledIfSystemProperty(named = "java.version", matches = "1.8.0_212")
    // Solo se ejecuta si existe la variable de sistema java.version y es igual a 1.8.0_212
    @Test
    void propertieTest() {
        assertEquals(1, 1);
    }

    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = "C:/Program Files/Java/jdk1.8.0_212")
    // Solo se ejecuta si existe la variable de entorno/ambiente JAVA_HOME y es igual a C:\Program Files\Java\jdk1.8.0_212
    @Test
    void environmetTest() {
        assertEquals(1, 1);
    }


    @EnabledIf("shouldExecuteTest") // Se ejecutara en funcion de la condicion de la funcion shouldExecuteTest
    @Test
    void customTest() {
        assertEquals(1, 1);
    }

    boolean shouldExecuteTest() {
        return true;
    }

    @Test
    void printVariablesSystem(){
        System.getenv().forEach(
                (key, value) -> {
                    System.out.println(key + " -> " + value);
                }
        );
    }

    @Test
    void printPropertiesSystem(){
        System.getProperties().forEach(
                (key, value) -> {
                    System.out.println(key + " -> " + value);
                }
        );
    }

}
