package org.gmarquezp.junit5.ejemplos.models;

import org.gmarquezp.junit5.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
    void testEstadoCuenta() {
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
    @DisplayName("Test para retirar dinero de una cuenta")  // Anotacion para formatear el nombre del metodo de prueba que se mostrara si falla
    void testRetirarDineroCuenta() throws DineroInsuficienteException {
        BigDecimal saldoInicial = new BigDecimal("1000.2321");
        BigDecimal valorRetirar = new BigDecimal("500.2321");
        Cuenta cuenta = new Cuenta("Juan", "123456789", saldoInicial);


        // asserNotNull(a) // verifica que no sea null
        // como 2do parametro o segun sea se pasa el string del mensaje
        assertNotNull(cuenta.getSaldo(), "El saldo de la cuenta no puede ser null");

        cuenta.retirar(valorRetirar);
        assertEquals(saldoInicial.subtract(valorRetirar), cuenta.getSaldo(), "El saldo de la cuenta no es correcto");


        cuenta.setEstaActiva(false);
        cuenta.retirar(new BigDecimal(100));
        // .toPlainString() // devuelve el valor de la cuenta en formato String
        assertEquals("500.0000", cuenta.getSaldo().toPlainString(), "El saldo de la cuenta no es correcto");

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
    void testDineroInsufiencienteException() {
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

    @Test
    void testTransferirDineroCuentas() throws DineroInsuficienteException {
        BigDecimal saldoInicial = new BigDecimal("1000.2321");
        BigDecimal valorTransferir = new BigDecimal("500.2321");
        Cuenta cuenta = new Cuenta("Juan", "123456789", saldoInicial);
        Cuenta cuenta2 = new Cuenta("Gustavo", "8552241", saldoInicial);

        Banco banco = new Banco("Bancolombia", "123456789", "5222");
        banco.transferir(cuenta, cuenta2, valorTransferir);

        assertEquals(saldoInicial.subtract(valorTransferir), cuenta.getSaldo());
        assertEquals(saldoInicial.add(valorTransferir), cuenta2.getSaldo());


        // Verifica que la cuenta no pueda retirar mas dinero de lo que tiene y lance una excepcion
        assertThrows(DineroInsuficienteException.class, () -> {
            banco.transferir(cuenta, cuenta2, new BigDecimal(9000000));
        });

    }

    @Test
    void testRelacionBancoConCuentas() {
        BigDecimal saldoInicial = new BigDecimal("1000.2321");
        BigDecimal valorTransferir = new BigDecimal("500.2321");
        Cuenta cuenta = new Cuenta("Juan", "123456789", saldoInicial);
        Cuenta cuenta2 = new Cuenta("Gustavo", "8552241", saldoInicial);

        Banco banco = new Banco("Bancolombia", "123456789", "5222");
        banco.addCuenta(cuenta)
                .addCuenta(cuenta2);

        // Comrprobando que se tengan 2 cuentas
        assertEquals(2, banco.getCuentas().size());

        // Comprobando el nombre del banco atraves de la relacion de cuenta banco
        cuenta.setBanco(new Banco("Colpatria", "123456789", "5222"));
        assertEquals("Colpatria", cuenta.getBanco().getNombre());

        // Comprobando que de las cuentas añadidas al banco, alguna contenga el nombre Gustavo
        assertEquals("Gustavo", banco.getCuentas().stream()
                .filter(cta -> cta.getNombre().equals("Gustavo"))
                .findFirst()
                .get()
                .getNombre());

        // Verifica que almenos una de las cuentas debe contener el nombre Juan
        assertTrue(banco.getCuentas().stream()
                .anyMatch(cta -> cta.getNombre().equals("Juan")));

    }

    @Test
    void testRelacionBancoConCuentasAssertAll() {

        // assertAll  // permite agrupar exepciones en lambdasm y lanzar en una sola excepcion, el resumen de todas las que fallaron

        BigDecimal saldoInicial = new BigDecimal("1000.2321");
        Cuenta cuenta = new Cuenta("Juan", "123456789", saldoInicial);
        cuenta.setBanco(new Banco("Colpatria", "123456789", "5222"));

        Cuenta cuenta2 = new Cuenta("Gustavo", "8552241", saldoInicial);
        cuenta2.setBanco(new Banco("Av Villas", "123456789", "5222"));

        Banco banco = new Banco("Bancolombia", "123456789", "5222");
        banco.addCuenta(cuenta)
                .addCuenta(cuenta2);

        // Comrprobando que se tengan 2 cuentas
        assertAll(
                () -> assertEquals(2, banco.getCuentas().size()),
                () -> assertEquals("Colpatria", cuenta.getBanco().getNombre()),

                () -> assertEquals("Gustavo", banco.getCuentas().stream()
                        .filter(cta -> cta.getNombre().equals("Gustavo"))
                        .findFirst()
                        .get()
                        .getNombre()),
                () -> assertTrue(banco.getCuentas().stream()
                        .anyMatch(cta -> cta.getNombre().equals("Juan")))
        );
    }

    @Test
    @Disabled // Salta la prueba y no la ejecuta
    void testParaSaltarUnaPrueba() {
        // fail // fuerza la prueba a fallar
        fail();
    }

}