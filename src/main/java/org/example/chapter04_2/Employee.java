package org.example.chapter04_2;

/**
 * 집약(Aggregation)에서 독립적으로 존재하는 객체.
 * Department가 없어도 Employee는 존재할 수 있다.
 */
public class Employee {

    private final String id;
    private final String name;
    private String position;

    public Employee(String id, String name, String position) {
        this.id       = id;
        this.name     = name;
        this.position = position;
    }

    public String getId()       { return id;       }
    public String getName()     { return name;     }
    public String getPosition() { return position; }

    public void promote(String newPosition) {
        System.out.println(name + " 직급 변경: " + position + " → " + newPosition);
        this.position = newPosition;
    }

    @Override
    public String toString() {
        return "Employee{id='" + id + "', name='" + name + "', position='" + position + "'}";
    }
}
