package org.gmarquezp.junit5.ejemplos.mockito;

import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.services.EmployeeService;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class VerifyTest {

    /**
     * Verify
     * - Verifica que un metodo fue llamado
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

        verify(this.employeeRepository).findOne(anyLong()); // Verifica que se llamo al metodo findOne
        verify(this.employeeRepository, times(1)).findOne(anyLong()); // Verifica que se llamo al metodo findOne una vez
        verify(this.employeeRepository, atLeastOnce()).findOne(anyLong()); // Verifica que se llamo al metodo findOne al menos una vez
        verify(this.employeeRepository, never()).count(); // Verifica que nunca se llamo al metodo count

        this.employeeService.findOne(1L);
        this.employeeService.findOne(2L);
        verify(this.employeeRepository, times(3)).findOne(anyLong()); // Verifica que se llamo al metodo findOne 3 veces


    }
}
