package org.gmarquezp.junit5.ejemplos.models;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class RepetedTest {
    /**
     * Permite repitir test N veces, si cumplen con la condición
     * - Util cuando se cuente con alguna aleatoriedad
     */


    // value = 5, se repite 5 veces
    @RepeatedTest(value = 5)
    void randomNumber() {
        Integer random = new Random().nextInt(10);

        System.out.println("Número aleatorio: " + random);

        assertTrue(random > 2);

    }

    @RepeatedTest(value = 5, name = "Repetición número Double: {currentRepetition} de {totalRepetitions}")
    void randomNumberDouble() {
        Double random = new Random().nextDouble(100);

        System.out.println("Número aleatorio double: " + random);

        assertTrue(random > 20);

    }
}
