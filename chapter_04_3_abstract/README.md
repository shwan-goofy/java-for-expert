# Chapter 4-3: 추상 클래스와 인터페이스

## 학습 목표

- `abstract class`와 `interface`의 선택 기준을 이해한다
- `interface`의 `default`/`static`/`private` 메서드를 이해한다
- `@FunctionalInterface`와 람다 표현식의 연결 고리를 이해한다
- JDK 17 `sealed interface`와 `permits`로 허용된 구현체를 제한한다

## Python과의 비교

| Python | Java |
|--------|------|
| `from abc import ABC, abstractmethod` | `abstract class` 또는 `interface` |
| `class Payment(ABC):` | `abstract class Payment` 또는 `interface Payment` |
| `@abstractmethod` | 반환타입 앞에 없음 — 인터페이스 메서드는 모두 암묵적 abstract |
| 다중 상속 | 인터페이스 다중 구현 (`implements A, B`) |

## 핵심 개념

### 1. abstract class vs interface 선택 기준

| 구분 | abstract class | interface |
|------|---------------|-----------|
| 상속 | 단일 (`extends`) | 다중 (`implements`) |
| 필드 | 인스턴스 필드 가능 | `public static final` 상수만 |
| 생성자 | 있음 | 없음 |
| 메서드 | 구현 포함 가능 | `default`, `static` 가능 |
| 용도 | 공통 상태/구현 공유 | 능력(계약) 정의 |

**abstract class 선택**: Is-a 관계 + 공통 상태(필드)나 구현을 공유할 때

**interface 선택**: 능력이나 계약을 정의할 때, 다중 구현이 필요할 때

### 2. abstract class

직접 인스턴스화 불가. 공통 코드와 강제 구현 메서드를 함께 제공.

```java
public abstract class Animal {
    protected final String name;

    public Animal(String name) { this.name = name; }

    // 공통 구현
    public String getName() { return name; }

    // 서브클래스가 반드시 구현해야 함
    public abstract String speak();
}
```

### 3. interface

능력(계약)을 정의한다. 상태 없이 행위만 선언한다.

```java
public interface Payable {
    boolean pay(int amount);  // 암묵적 abstract public

    default String getPaymentType() {  // default 메서드 (JDK 8+)
        return "UNKNOWN";
    }

    static Payable noOp() {  // static 팩토리 메서드 (JDK 8+)
        return amount -> true;
    }

    private void logPayment(int amount) {  // private 메서드 (JDK 9+)
        System.out.println("결제 로그: " + amount);
    }
}
```

### 4. @FunctionalInterface와 람다

추상 메서드가 **정확히 하나**인 인터페이스는 람다로 구현할 수 있다.
`@FunctionalInterface`를 붙이면 조건 위반 시 컴파일 에러가 발생한다.

```java
@FunctionalInterface
public interface DiscountStrategy {
    int apply(int price);  // 단 하나의 추상 메서드
}

// 람다로 구현
DiscountStrategy tenPercent = price -> (int) (price * 0.9);
DiscountStrategy fixed1000  = price -> price - 1000;
```

### 5. sealed interface (JDK 17)

허용된 구현체만 이 인터페이스를 구현할 수 있다.
`permits`에 명시된 클래스만 허용되므로 switch expression의 완전성 검사가 가능하다.

```java
public sealed interface Shape permits Circle, Rectangle, Triangle {}

public record Circle(double radius) implements Shape {}
public record Rectangle(double w, double h) implements Shape {}
public record Triangle(double base, double height) implements Shape {}

// 모든 허용 구현체를 처리하지 않으면 컴파일 에러
double area = switch (shape) {
    case Circle c    -> Math.PI * c.radius() * c.radius();
    case Rectangle r -> r.w() * r.h();
    case Triangle t  -> 0.5 * t.base() * t.height();
};
```

## 실습 예제

`Payment.java`에서는 다음을 학습한다:
- abstract class (공통 상태 + 추상 메서드)
- interface (순수 계약)
- @FunctionalInterface

`CreditCardPayment.java`에서는 다음을 학습한다:
- abstract class 상속 + interface 구현

`Shape.java`에서는 다음을 학습한다:
- sealed interface + record 구현체

`AbstractMain.java`에서는 다음을 학습한다:
- 다형성 활용
- 람다로 FunctionalInterface 구현
- sealed interface + switch expression

## 주의사항

- 추상 클래스는 구현을 공유하는 계층에서만 사용한다 — 무분별한 상속 트리 금지
- `interface`의 `default` 메서드는 다중 상속 시 다이아몬드 문제가 생길 수 있다 (명시적 override로 해결)
- `@FunctionalInterface`가 없어도 단일 추상 메서드이면 람다 사용 가능 — 하지만 어노테이션으로 의도를 명시한다

## 다음 챕터

[Chapter 5-1: SRP (단일 책임 원칙)](../chapter_05_1_srp/)에서는 SOLID 원칙을 배운다.
