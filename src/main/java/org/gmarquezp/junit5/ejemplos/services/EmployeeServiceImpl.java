package org.gmarquezp.junit5.ejemplos.services;

import org.gmarquezp.junit5.ejemplos.models.Employee;
import org.gmarquezp.junit5.ejemplos.repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Integer count() {
        return this.employeeRepository.count();
    }

    @Override
    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee findOne(Long id) {
        return this.employeeRepository.findOne(id);
    }

    @Override
    public Optional<Employee> findOneOptional(Long id) {

        try {
            return Optional.of(this.employeeRepository.findOne(id));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public List<Employee> saveAll(List<Employee> employees) {
        return this.employeeRepository.saveAll(employees);
    }

    @Override
    public boolean delete(Long id) {
        return this.employeeRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        this.employeeRepository.deleteAll();
    }

    @Override
    public void calculateSalary(Long id) {
        // Funcion util solo para verificar que se llamand dos funciones en cierto orden
        Employee employee = this.findOne(id);
        Integer countEmployees = this.count();

        if (employee.getAge() > 18) {
            employee.setSalary(1000.0);
        } else if (countEmployees > 10) {
            employee.setSalary(500.0);
        } else {
            employee.setSalary(300.0);
        }

        System.out.println("EmployeeServiceImpl.calculateSalary: " + employee.getSalary());
    }
}