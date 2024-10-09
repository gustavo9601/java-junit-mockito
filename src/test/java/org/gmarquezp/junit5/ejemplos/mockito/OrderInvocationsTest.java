package org.gmarquezp.junit5.ejemplos.mockito;


import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderInvocationsTest {
    @Mock // Crea un mock
    EmployeeRepository employeeRepository;

    // System under test
    @InjectMocks // Inyecta el mock
            EmployeeServiceImpl employeeService;


    @Test
    void calculateSalaryTest() {
        // given
        Employee employeeDummy = new Employee(1L, "GUs", 28);
        when(this.employeeRepository.findOne(any())).thenReturn(employeeDummy); // Cuando se llame -> findOne, mockeara a employeeDummy
        when(this.employeeRepository.count()).thenReturn(1);

        // when
        this.employeeService.calculateSalary(1L);

        // then
        verify(this.employeeRepository, times(1)).findOne(anyLong());
        verify(this.employeeRepository, times(1)).count();

        // Verificando el orden de llamada
        InOrder inOrder = inOrder(this.employeeRepository);
        // Verifica que primero se llamo a findOne y luego a count, en caso contrario fallara
        inOrder.verify(this.employeeRepository).findOne(anyLong());
        inOrder.verify(this.employeeRepository).count();

    }
}
