package org.example.chapter04_1;

/**
 * 부모 클래스 Animal.
 * 공통 필드와 기본 행위를 정의한다.
 */
public class Animal {

    protected final String name;
    private int age;

    public Animal(String name, int age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다");
        }
        this.name = name;
        this.age  = age;
    }

    public String getName() { return name; }
    public int getAge()     { return age;  }

    // 자식 클래스에서 오버라이딩할 수 있는 메서드
    public String speak() {
        return name + ": ...";
    }

    public String describe() {
        return name + " (나이: " + age + ")";
    }

    // final 메서드 — 자식 클래스에서 오버라이딩 불가
    public final String getSpecies() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getSpecies() + "{name='" + name + "', age=" + age + "}";
    }
}
