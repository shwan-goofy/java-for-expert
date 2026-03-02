package org.example.chapter04_2;

import java.util.ArrayList;
import java.util.List;

/**
 * 집약(Aggregation) 예시.
 * Department는 Employee를 외부에서 주입받는다 — 약한 Has-a 관계.
 * Department가 소멸되어도 Employee는 독립적으로 존재한다.
 */
public class Department {

    private final String name;
    private final List<Employee> employees;

    public Department(String name) {
        this.name      = name;
        this.employees = new ArrayList<>();
    }

    // Employee는 외부에서 만들어져 주입됨 (집약)
    public void addEmployee(Employee employee) {
        employees.add(employee);
        System.out.println(employee.getName() + "이(가) " + name + " 부서에 합류");
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        System.out.println(employee.getName() + "이(가) " + name + " 부서를 떠남");
    }

    public List<Employee> getEmployees() {
        return List.copyOf(employees);
    }

    public String getName() { return name; }

    public void printRoster() {
        System.out.println("=== " + name + " 부서 명단 ===");
        employees.forEach(e -> System.out.println("  " + e));
    }
}
