package org.gmarquezp.junit5.ejemplos.mockito;

import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.services.EmployeeService;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Opcion 1
 *
 * @ExtendWith(MockitoExtension.class) // Inicializa los mocks, sin necesida de ahcer el @BeforeEach con openMocks
 */

@ExtendWith(MockitoExtension.class)
public class InjectMockTest {


    // Dependencia
    @Mock // Crea un mock
    EmployeeRepository employeeRepository;

    // System under test
    @InjectMocks // Inyecta el mock
    EmployeeServiceImpl employeeService;


    /**
     * Opcion 2
     *
     *  @BeforeEach
     *     void setUp() {
     *         MockitoAnnotations.openMocks(this); // Inicializa los mocks
     *     }
     * */


    @Test
    void findOneTest() {

        // given
        Employee employeeDummy = new Employee(1L, "GUs", 28);
        when(this.employeeRepository.findOne(any())).thenReturn(employeeDummy); // Cuando se llame -> findOne, mockeara a employeeDummy

        // when
        Employee employee = this.employeeService.findOne(1L);

        // then
        assertEquals(employeeDummy.getName(), employee.getName());
        assertNotNull(employee); // Verifica que no sea nulo
    }
}
