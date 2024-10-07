package org.gmarquezp.junit5.ejemplos.mockito;

import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.services.EmployeeService;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArgumentMatcherTest {

    /**
     * Paremtros propios de mockito
     * - any() -> Cualquier valor
     * - anyInt() -> Cualquier valor entero
     * - anyString() -> Cualquier valor string
     * .. etc
     *
     *
     * */

    // Dependencia
    EmployeeRepository employeeRepository;

    // System under test
    EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        // Creando el mock
        this.employeeRepository = mock(EmployeeRepository.class);
        this.employeeService = new EmployeeServiceImpl(this.employeeRepository);
    }

    @Test
    void findOneTest() {

        // given
        Employee employeeDummy = new Employee(1L, "GUs", 28);
        when(this.employeeRepository.findOne(anyLong())).thenReturn(employeeDummy); // Cuando se llame -> findOne, mockeara a employeeDummy

        // when
        Employee employee = this.employeeService.findOne(anyLong());

        // then
        assertEquals(employeeDummy.getName(), employee.getName());
        assertNotNull(employee); // Verifica que no sea nulo
    }
}
