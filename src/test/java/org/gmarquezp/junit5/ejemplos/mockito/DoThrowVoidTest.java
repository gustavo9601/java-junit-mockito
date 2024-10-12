package org.gmarquezp.junit5.ejemplos.mockito;

import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoThrowVoidTest {

    @Mock // Crea un mock
    EmployeeRepository employeeRepository;

    // System under test
    @InjectMocks // Inyecta el mock
            EmployeeServiceImpl employeeService;

    @Test
    void name() {

        // Emulara que .saveAll() lance la excepcion
        doThrow(new RuntimeException()).when(this.employeeRepository).saveAll(anyList());

        // Como saveAll del repository se emulo que lanza una excecion, entonces el servicio tambien deberia porpagar la excepcion
        assertThrows(RuntimeException.class, () -> this.employeeService.saveAll(List.of(
                new Employee(1L, "Gus", 28)
        )));
    }
}
