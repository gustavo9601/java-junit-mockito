package org.gmarquezp.junit5.ejemplos.mockito;


import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.services.EmployeeService;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomArgumentMatcher {
    /**
     * Permite verificar que los argumentos pasados a funciones mockeadas, si sean los esperados
     * - Util cuando en la funcion SUT, se pasan argumentos que pueden cambiar el parametro recibido y que a su ves se pasa a otra funcion
     */


    // Dependencia
    EmployeeRepository employeeRepository;

    // System under test
    EmployeeService employeeService;


    @BeforeEach
    void setUp() {
        this.employeeRepository = mock(EmployeeRepository.class);
        this.employeeService = new EmployeeServiceImpl(this.employeeRepository);
    }


    @Test
    void name() {

        List<Employee> employeess = List.of(
                new Employee(1L, "GUs", 28),
                new Employee(2L, "Laura", 30),
                new Employee(3L, "Osvaldo", 25)
        );

        // Service
        this.employeeService.saveAll(employeess);

        // Verificaciones
        verify(this.employeeRepository).saveAll(argThat(argument -> argument.size() == 3)); // Verifica que se llamo al metodo saveAll del repository con una lista de 3 elementos
        verify(this.employeeRepository).saveAll(argThat(argument -> argument.get(0).getName().equals("GUs"))); // Verifica que se llamo al metodo saveAll del repository, donde el primer elemento tiene el nombre GUs


    }
}
