package org.gmarquezp.junit5.ejemplos.repositories;

import org.gmarquezp.junit5.ejemplos.models.Employee;

import java.util.List;

public interface EmployeeRepository {

    Integer count();

    List<Employee> findAll();

    Employee findOne(Long id);

    Employee save(Employee smartphone);

    List<Employee> saveAll(List<Employee> employees);

    boolean delete(Long id);

    void deleteAll();

}
