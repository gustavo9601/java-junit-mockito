package org.gmarquezp.junit5.ejemplos.mockito;

import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepositoryImpl;
import org.gmarquezp.junit5.ejemplos.services.EmployeeService;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


public class MockVsSpyTest {

    /**
     * Mock: Objeto fictisio que simula el comportamiento de sus metodos
     * <p>
     * Spy: Objeto que llama a los metodos reales del objeto
     * - Ejecutarias codigo, solo usar cuando dependamos de un tercero
     * - Cuando se requiera realizar verificaciones ciertos metodos
     * - No se puede crar un spy de una clase abstracta o interfaz
     */

    // Dependencia

    EmployeeRepository employeeRepository;

    // System under test
    EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        this.employeeRepository = spy(EmployeeRepositoryImpl.class); // Con clase concreta
        this.employeeService = new EmployeeServiceImpl(this.employeeRepository);
    }

    @Test
    void findOneTest() {

        // given
        this.employeeService.findOne(1L);
        verify(this.employeeRepository, times(1)).findOne(any());

    }

    @Test
    void findOneTest2() {
// Como estamos usando Spy, quemaremos el resultado pero el metodo si se ejecutara


        // given
        Employee employee = new Employee(1L, "GUs", 28);
        when(this.employeeRepository.findOne(1L)).thenReturn(employee);

        // when
        Employee employeeResult = this.employeeService.findOne(1L);

        // then
        assertEquals(employee.getName(), employeeResult.getName());
        verify(this.employeeRepository, times(1)).findOne(any());

    }

    @Test
    void findOneTestDoReturn() {

        // con doReturn => no se ejecutara el metodo real, y se comportara como un mock

        // given
        Employee employee = new Employee(1L, "GUs", 28);
        doReturn(employee).when(this.employeeRepository).findOne(1L);

        // when
        Employee employeeResult = this.employeeService.findOne(1L);

        // then
        assertEquals(employee.getName(), employeeResult.getName());
        verify(this.employeeRepository, times(1)).findOne(any());

    }


}
