package org.gmarquezp.junit5.ejemplos.mockito;

import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;
import org.gmarquezp.junit5.ejemplos.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoNothingVoidTest {


    @Mock // Crea un mock
    EmployeeRepository employeeRepository;

    // System under test
    @InjectMocks // Inyecta el mock
            EmployeeServiceImpl employeeService;

    @Test
    void name() {
        // Emulara que .deleteAll() no haga nada
        doNothing().when(this.employeeRepository).deleteAll();

        this.employeeService.deleteAll(); // No debe hacer nada cuando se llama al deleteAll del repository

        verify(this.employeeRepository, times(1)).deleteAll(); // Verifica que se llamo al metodo deleteAll del repository
    }
}
