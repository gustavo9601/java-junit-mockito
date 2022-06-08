package org.gmarquezp.junit5.ejemplos.models;

import org.gmarquezp.junit5.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
    // Siempre se coloca la anotacion @Test para indicar que es un metodo de prueba
    @Test
    void testObjetoCuenta() {
        Cuenta cuenta = new Cuenta("Juan", "123456789", new BigDecimal("1000.2321"));
        // assertEquals(a, b) // verifica que tengan el mismo valor
        assertEquals("Juan", cuenta.getNombre());
        assertEquals("123456789", cuenta.getNumeroDeCuenta());
        assertEquals(new BigDecimal("1000.2321"), cuenta.getSaldo());

        cuenta.setEstaActiva(true);
        // assertTrue(a) // verifica que sea true
        assertTrue(cuenta.getEstaActiva());
    }

    @Test
    void saldoCuenta() {
        Cuenta cuenta = new Cuenta("Juan", "123456789", new BigDecimal("1000.2321"));
        // doubleValue // devuelve el valor de la cuenta en formato double
        assertEquals(1000.2321, cuenta.getSaldo().doubleValue());
    }

    @Test
    void testEstadoCuenta(){
        Cuenta cuenta = new Cuenta("Juan", "123456789", new BigDecimal("1000.2321"));
        assertTrue(cuenta.getEstaActiva());
        cuenta.setEstaActiva(false);

        // assertFalse(a) // verifica que sea false
        assertFalse(cuenta.getEstaActiva());
        // la cuenta no debe ser mayor a 0
        assertFalse(cuenta.getSaldo().doubleValue() < 0);
    }

    @Test
    void testReferenciaDeCuenta() {
        Cuenta cuenta = new Cuenta("Juan", "123456789", new BigDecimal("1000.2321"));
        Cuenta cuenta2 = new Cuenta("Juan", "123456789", new BigDecimal("1000.2321"));
        Cuenta cuenta3 = cuenta;
        // assertSame(a, b) // verifica que sean el mismo objeto
        assertSame(cuenta, cuenta3);

        // Comparando con equals del objeto
        cuenta.setId("123456789");
        cuenta2.setId("123456789");
        assertEquals(cuenta, cuenta2);
    }


    @Test
    void testRetirarDineroCuenta() throws DineroInsuficienteException {
        BigDecimal saldoInicial = new BigDecimal("1000.2321");
        BigDecimal valorRetirar = new BigDecimal("500.2321");
        Cuenta cuenta = new Cuenta("Juan", "123456789", saldoInicial);


        // asserNotNull(a) // verifica que no sea null
        assertNotNull(cuenta.getSaldo());

        cuenta.retirar(valorRetirar);
        assertEquals(saldoInicial.subtract(valorRetirar), cuenta.getSaldo());


        cuenta.setEstaActiva(false);
        cuenta.retirar(new BigDecimal(100));
        // .toPlainString() // devuelve el valor de la cuenta en formato String
        assertEquals("500.0000", cuenta.getSaldo().toPlainString());

    }

    @Test
    void testDepositarCuenta() {
        BigDecimal saldoInicial = new BigDecimal("1000.2321");
        BigDecimal valorDepositar = new BigDecimal("500.2321");
        Cuenta cuenta = new Cuenta("Juan", "123456789", saldoInicial);

        cuenta.depositar(valorDepositar);
        assertEquals(saldoInicial.add(valorDepositar), cuenta.getSaldo());

        cuenta.setEstaActiva(false);
        cuenta.depositar(new BigDecimal(100));
        assertEquals("1500.4642", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsufiencienteException(){
        BigDecimal saldoInicial = new BigDecimal("1000.2321");
        BigDecimal valorRetirar = new BigDecimal("50000.2321");
        Cuenta cuenta = new Cuenta("Juan", "123456789", saldoInicial);
        // assertThrows(a, b) // verifica que se lanze la excepcion
        Exception exceptionEsperada = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.retirar(valorRetirar);
        });
        // verificando el mensaje de la exepcion obtenida
        assertEquals("No se puede retirar más dinero de lo que hay en la cuenta", exceptionEsperada.getMessage());
    }
}