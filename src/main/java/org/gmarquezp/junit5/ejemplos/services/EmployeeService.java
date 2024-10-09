package org.gmarquezp.junit5.ejemplos.services;

import org.gmarquezp.junit5.ejemplos.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Integer count();

    List<Employee> findAll();

    Employee findOne(Long id);

    Optional<Employee> findOneOptional(Long id);

    Employee save(Employee employee);

    List<Employee> saveAll(List<Employee> employees);

    void calculateSalary(Long id);

    boolean delete(Long id);

    void deleteAll();
}
