package org.gmarquezp.junit5.ejemplos.models;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TagCategorizarTest {
    /**
     * Ejecutar test por tags
     * <p>
     * mvn test -Dgroups="tag-name"
     * mvn test -Dgroups="tag-name, tag-name2" // Ejecutar varios tags
     */

    // mvn test -Dgroups="development"
    @Tag("development")
    @Test
    void name() {
        System.out.println("development 1");
    }

    @Tag("development")
    @Test
    void nam2() {
        System.out.println("development 2");
    }

    @Tag("production")
    @Test
    void name3() {
        System.out.println("production 1");
    }
}
