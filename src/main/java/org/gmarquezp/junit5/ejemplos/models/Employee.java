package org.gmarquezp.junit5.ejemplos.models;

public class Employee {
    private Long id;
    private String name;
    private Integer age;

    private Double salary;

    public Employee(){}

    public Employee(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
