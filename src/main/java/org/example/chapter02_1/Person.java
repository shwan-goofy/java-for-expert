package org.example.chapter02_1;

/**
 * 생성자 오버로딩과 this() 체이닝 예시.
 */
public class Person {

    private final String name;
    private final int age;
    private final String email;
    private final String role;

    // 핵심 생성자 — 모든 초기화 로직이 여기에 집중
    public Person(String name, int age, String email, String role) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다");
        }
        if (age < 0) {
            throw new IllegalArgumentException("나이는 0 이상이어야 합니다: " + age);
        }
        this.name  = name;
        this.age   = age;
        this.email = email;
        this.role  = role;
    }

    // this() 체이닝 — 기본값으로 핵심 생성자에 위임
    public Person(String name, int age, String email) {
        this(name, age, email, "USER");  // role 기본값
    }

    public Person(String name, int age) {
        this(name, age, "");  // email 기본값
    }

    public Person(String name) {
        this(name, 0);  // age 기본값
    }

    public String getName()  { return name;  }
    public int    getAge()   { return age;   }
    public String getEmail() { return email; }
    public String getRole()  { return role;  }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age
                + ", email='" + email + "', role='" + role + "'}";
    }
}
