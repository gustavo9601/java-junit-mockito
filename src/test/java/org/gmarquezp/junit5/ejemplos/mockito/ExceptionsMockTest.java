package org.gmarquezp.junit5.ejemplos.mockito;

import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Opcion 1
 *
 * @ExtendWith(MockitoExtension.class) // Inicializa los mocks, sin necesida de ahcer el @BeforeEach con openMocks
 */

@ExtendWith(MockitoExtension.class)
public class ExceptionsMockTest {


    // Dependencia
    @Mock // Crea un mock
            EmployeeRepository employeeRepository;

    // System under test
    @InjectMocks // Inyecta el mock
            EmployeeServiceImpl employeeService;


    /**
     * Opcion 2
     *
     * @BeforeEach void setUp() {
     * MockitoAnnotations.openMocks(this); // Inicializa los mocks
     * }
     */


    @Test
    void checkException() {
        // given
        // Cuando se busque un empleado con id null, lanzara una excepcion
        when(this.employeeRepository.findOne(any())).thenThrow(new IllegalArgumentException());

        // when
        RuntimeException exception = null;
        Optional<Employee> employeeOpt = this.employeeService.findOneOptional(null);


        // then
        assertNotNull(employeeOpt);
        assertFalse(employeeOpt.isPresent()); // Verifica que sea false

        // Verifica que se lanzo una excepcion
        /**
         * - Se valida si se lanza, pero si se controla no pasara el test
         *
         * assertThrows(IllegalArgumentException.class, () -> {
            this.employeeService.findOneOptional(null);
        });*/
    }
}
