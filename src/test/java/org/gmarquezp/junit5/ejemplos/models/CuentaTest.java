package org.gmarquezp.junit5.ejemplos.models;

import org.gmarquezp.junit5.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


/*
* Ejecutar por consola
* - Instalar en el pom
*
*         <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.2</version>

                <!--Para poder especificar que etiquetas debe ejecutar-->
                <!--<configuration>
                    <groups>cuenta</groups>
                </configuration>-->
            </plugin>
*
*  // Ejecuta tods los test
*  mvn test
*
* */
class CuentaTest {

    @BeforeAll
    // Se ejecuta una sola vez antes de todos los test
    static void initMetodoTestAll() {
        System.out.println("Iniciando primera ves test");
    }

    @AfterAll
    // Se ejecuta una sola vez despues de todos los test
    static void endMetodoTestAll() {
        System.out.println("Finalizando de ultimas ves test");
    }

    @BeforeEach
        // Se ejecuta antes de cada método de prueba
    // TestInfo => interfaz injectada por JUnit5 que permite obtener informacion de la prueba en si
    void initMetodoTest(TestInfo testInfo, TestReporter testReporter) {
        System.out.println("*".repeat(10) + " Iniciando metodo test " + "*".repeat(10));

        // Usando el log propio de Junit testReporter
        testReporter.publishEntry(" ejecutando: " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().orElse(null).getName()
                + " con las etiquetas " + testInfo.getTags());
    }

    @AfterEach
        // Se ejecuta antes de cada método de prueba
    void endMetodoTest() {
        System.out.println("Finalizando metodo test");
    }

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
    @DisplayName("Test para retirar dinero de una cuenta")
        // Anotacion para formatear el nombre del metodo de prueba que se mostrara si falla
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
    @Disabled
        // Salta la prueba y no la ejecuta
    void testParaSaltarUnaPrueba() {
        // fail // fuerza la prueba a fallar
        fail();
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
        // Es un condicional que valida en funcion del SO
    void testSoloWindows() {
        System.out.println("Solo windows");
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloMac() {
        System.out.println("Solo Mac");
    }

    @Test
    @EnabledOnJre({JRE.JAVA_11, JRE.JAVA_17})
        // Se ejecuta solo dependiendo del JRE pasado por parametro
    void soloEnUnJdk() {
        System.out.println("Solo en un JDK");
    }

    @Test
    // @EnabledIfSystemProperties(named = "user.name", matches = ".*gus.*")
    // Se ejecuta solo dependiendo de las propiedades del sistema
    // @DisabledIfSystemProperties(named = "user.name", matches = ".*gus.*")
    // No se ejecuta solo dependiendo de las propiedades del sistema
    @Disabled
    void testDependiendoSystemProperties() {
        System.out.println("Properties");
        System.getProperties().forEach((key, value) -> System.out.println(key + ": " + value));
    }


    @Test
    // Se ejecuta solo dependiendo de las propiedades del ambiente
    @EnabledIfEnvironmentVariable(named = "USERNAME", matches = ".*gus.*")
        // @DisabledIfEnvironmentVariable(named = "ENV", matches = "prod"); // No se ejecutara si encuentra la variable ENV y es prod

    void testVariablesDeEntrono() {
        Map<String, String> variableDeEnterno = System.getenv();
        // variables de entorno
        variableDeEnterno.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    @Test
    void testUsandoAsumptions() {
        boolean esDev = "dev".equals(System.getenv("ENV"));
        // La prueba Se ejecuta solo si es dev, en caso contrario se ignorara toda la prueba
        // Permite realizar condiciones de forma programatica y no con anotaciones
        assumeTrue(esDev);

        System.out.println("Solo si es dev");
    }

    @Nested
            // Permite agrupar pruebas dentro de una clase general, y dependiendo del IDE se visualizaran gerarquicamente
    class ClaseAgrupadoraPruebas {

        @Test
        void testPrueba1() {
            System.out.println("Prueba 1");
            assertTrue(true);
        }

        @Test
        void testPrueba2() {
            System.out.println("Prueba 2");
            assertEquals(1, 1);
        }
    }

    @RepeatedTest(5)
        // Se ejecutara N veces por parametro, util cuando son valores random
        // Dentro del IDE, se visualizara cada ejecucion si paso o no
    void repetirPrueba() {
        System.out.println("Repetir prueba");
        int aleatorio = new Random().nextInt(6);
        if (aleatorio > 5) {
            fail("la prueba fallo, el numero aleatorio es: " + aleatorio);
        }
    }


    @ParameterizedTest(name = "repeticion {index}, ejectando con valor {argumentsWithNames}")
    @Tag("prueba-parametrizada") // Permite añadir una agrupacion por tag, para poder ejecutarse solo con ese tag al tiempo la agrupacion
    // Para ejecutarla desde el IDE, seleccionar por Tag la ejecucion y colocar el valore respectivo
    @ValueSource(ints = {10, 20, 30, 40, 500})
        // Permite pasar una lista de valores
    void testConParametrosCuenta(int numero) throws DineroInsuficienteException { // Cada valor del value source sera iterado y pasado a la funcion
        System.out.println("Prueba con parametros");
        Cuenta cuenta = new Cuenta("Juan", "123456789", new BigDecimal("100"));
        cuenta.retirar(new BigDecimal(numero));
        assertNotNull(cuenta.getSaldo()); // Prueba que sea diferente de nullo
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0); // Prueba que sea mayor que cero
    }

    @ParameterizedTest()
    @Tag("prueba-parametrizada") // Permite añadir una agrupacion por tag, para poder ejecutarse solo con ese tag al tiempo la agrupacion
    @CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000.12345"})
        // permite emular una lista importada desde csv
    void testConParametrosCsvSource(int numero, double saldo) throws DineroInsuficienteException {
        System.out.println("Prueba con parametros");
        Cuenta cuenta = new Cuenta("Juan", "123456789", new BigDecimal("1000"));
        cuenta.retirar(new BigDecimal(saldo));
        assertNotNull(cuenta.getSaldo());
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest()
    @Tag("prueba-parametrizada") // Permite añadir una agrupacion por tag, para poder ejecutarse solo con ese tag al tiempo la agrupacion
    @CsvFileSource(resources = "/data.csv")
        // la informacion de archivo csv se importa desde un archivo
    void testConParametrosCsvFileSource(double saldo) throws DineroInsuficienteException {
        System.out.println("Prueba con parametros");
        Cuenta cuenta = new Cuenta("Juan", "123456789", new BigDecimal("1000"));
        cuenta.retirar(new BigDecimal(saldo));
        assertNotNull(cuenta.getSaldo());
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest()
    @Tag("prueba-parametrizada") // Permite añadir una agrupacion por tag, para poder ejecutarse solo con ese tag al tiempo la agrupacion
    @MethodSource("dataFake") // Especificamos un metodo que devuelva una lista de valores, para ejecutar por iteracion la prueba
    void testConParametrosDesdeMetodo(Integer saldo) throws DineroInsuficienteException {
        System.out.println("Prueba con parametros");
        Cuenta cuenta = new Cuenta("Juan", "123456789", new BigDecimal("1000"));
        cuenta.retirar(new BigDecimal(saldo));
        assertNotNull(cuenta.getSaldo());
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    private static List<Integer> dataFake() {
        return Arrays.asList(10, 20, 30, 40, 500);
    }
}
