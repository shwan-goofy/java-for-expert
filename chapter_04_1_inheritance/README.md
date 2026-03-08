# Chapter 4-1: 상속

## 학습 목표

- Java의 단일 상속 구조와 `extends`를 이해한다
- `@Override`, `super()`, `super.메서드()`를 올바르게 사용한다
- 다형성(upcasting/downcasting)을 이해한다
- `final` 클래스/메서드로 상속을 제한한다
- JDK 16+ `pattern matching for instanceof`로 안전한 다운캐스팅을 한다

## Python과의 비교

| Python | Java |
|--------|------|
| `class Dog(Animal):` | `class Dog extends Animal` |
| `super().__init__()` | `super()` 생성자 체이닝 |
| 메서드 오버라이딩 (자동) | `@Override` 어노테이션 권장 |
| 다중 상속 가능 | 단일 상속만 (인터페이스로 다중 구현) |
| `isinstance(obj, Dog)` | `obj instanceof Dog` |
| 없음 | `obj instanceof Dog d` (pattern matching) |

## 핵심 개념

### 1. extends와 생성자 호출

자식 클래스 생성자는 **반드시 부모 생성자를 먼저 호출**해야 한다.
명시적으로 `super()`를 호출하지 않으면 컴파일러가 `super()`(기본 생성자)를 자동 삽입한다.

```java
public class Animal {
    protected final String name;

    public Animal(String name) {
        this.name = name;
    }
}

public class Dog extends Animal {
    private String breed;

    public Dog(String name, String breed) {
        super(name);  // 부모 생성자 호출 — 반드시 첫 번째 문장
        this.breed = breed;
    }
}
```

### 2. @Override와 메서드 재정의

`@Override`는 필수가 아니지만, 부모 메서드가 없을 때 컴파일 에러를 발생시켜 실수를 방지한다.

```java
public class Dog extends Animal {
    @Override
    public String speak() {
        return name + ": 멍멍";
    }
}
```

### 3. 다형성 (Polymorphism)

부모 타입 변수로 자식 인스턴스를 참조할 수 있다 (upcasting).
호출 메서드는 실제 인스턴스 타입 기준으로 결정된다 (동적 디스패치).

```java
Animal animal = new Dog("멍멍이", "진돗개");  // upcasting
animal.speak();  // "멍멍이: 멍멍" — Dog의 speak() 호출
```

### 4. final 클래스/메서드

- `final class`: 상속 불가 (`String`, `Integer` 등이 final)
- `final method`: 오버라이딩 불가

```java
public final class ImmutableValue { ... }  // 상속 불가

public class Animal {
    public final String getName() { return name; }  // 오버라이딩 불가
}
```

### 5. Pattern matching for instanceof (JDK 16+)

기존 `instanceof` + 캐스팅 + 변수 선언 세 단계를 하나로 합친다.

```java
// 기존 방식
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;
    dog.fetch();
}

// Pattern matching (JDK 16+)
if (animal instanceof Dog dog) {
    dog.fetch();  // dog는 이미 Dog 타입으로 바인딩됨
}
```

switch expression과 함께:
```java
String description = switch (animal) {
    case Dog d    -> d.getName() + " (품종: " + d.getBreed() + ")";
    case Cat c    -> c.getName() + " (실내: " + c.isIndoor() + ")";
    default       -> animal.getName() + " (기타)";
};
```

## 실습 예제

`Animal.java`에서는 다음을 학습한다:
- 부모 클래스 구조, protected 필드, abstract 메서드 없는 기본 메서드

`Dog.java`, `Cat.java`에서는 다음을 학습한다:
- `extends`, `super()`, `@Override`
- 각 동물별 고유 필드와 메서드

`InheritanceMain.java`에서는 다음을 학습한다:
- 다형성 배열에 다양한 동물 담기
- pattern matching for instanceof
- switch expression + type pattern

## 주의사항

- 상속은 Is-a 관계일 때만 사용한다 (Dog Is-a Animal — O, Stack Is-a Vector — X)
- `@Override`는 항상 붙인다
- 부모 클래스 생성자 호출(`super()`)은 자식 생성자의 첫 번째 문장이어야 한다
- downcasting(`(Dog) animal`)은 `instanceof` 확인 없이 하면 `ClassCastException` 위험

## 다음 챕터

[Chapter 4-2: 합성과 집약](../chapter_04_2_composition/)에서는 Has-a 관계를 배운다.
