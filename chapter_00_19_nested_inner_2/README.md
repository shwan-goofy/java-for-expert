# Chapter 0-19: 중첩 클래스, 내부 클래스 2

## 학습 목표

- 메서드 안에서만 사용하는 **지역 클래스(Local Class)**를 이해한다
- 이름 없이 즉석에서 만드는 **익명 클래스(Anonymous Class)**를 이해한다
- 지역 클래스/익명 클래스가 바깥의 지역 변수를 캡처하는 방식(effectively final)을 이해한다
- 인터페이스 구현체를 익명 클래스로 즉석에서 만드는 실전 패턴을 익힌다

## Python과의 비교

| Python | Java |
|---|---|
| 함수 안에 클래스를 정의하는 것이 가능하지만 드묾 | 지역 클래스: 메서드 안에서만 쓰이는 클래스를 그 메서드 안에 정의 |
| 람다(`lambda`)나 클로저로 즉석 함수 작성 | 익명 클래스: 인터페이스/추상클래스를 이름 없이 즉석 구현 (자바 8+ 에서는 람다가 대체하는 경우多) |
| 클로저는 자유 변수를 참조로 캡처 (재할당 가능) | 지역 클래스/익명 클래스는 바깥의 지역 변수를 **effectively final**(사실상 final)일 때만 캡처 가능 |

## 핵심 개념

### 1. 지역 클래스 (Local Class)

메서드, 생성자, 블록 **안에서 정의되는 클래스**다. 그 메서드 안에서만 사용되는 아주 지역적인 로직을
클래스로 묶고 싶을 때 사용한다. 실무에서는 자주 쓰이지 않지만 개념은 알아둘 필요가 있다.

```java
public void processOrders(List<Integer> amounts) {
    int discountRate = 10; // effectively final — 이후 재할당되지 않음

    class DiscountCalculator { // 지역 클래스 — processOrders 메서드 안에서만 존재
        int apply(int amount) {
            return amount - (amount * discountRate / 100); // 바깥 지역 변수 캡처
        }
    }

    DiscountCalculator calculator = new DiscountCalculator();
    for (int amount : amounts) {
        System.out.println(calculator.apply(amount));
    }
}
```

### 2. 익명 클래스 (Anonymous Class)

이름 없이, **인터페이스나 추상 클래스를 즉석에서 구현**하면서 동시에 객체를 생성하는 문법이다.
단 한 번만 사용할 구현체를 만들 때 별도의 클래스 파일을 만들 필요가 없어 편리하다.

```java
public interface Validator {
    boolean isValid(String input);
}

Validator notEmpty = new Validator() { // 익명 클래스 — Validator를 즉석에서 구현
    @Override
    public boolean isValid(String input) {
        return input != null && !input.isEmpty();
    }
};

System.out.println(notEmpty.isValid("hello")); // true
```

### 3. 지역 변수 캡처와 effectively final

지역 클래스/익명 클래스는 자신을 감싸는 메서드의 지역 변수를 참조할 수 있다.
단, 그 변수가 **사실상 final(effectively final, 선언 후 재할당되지 않음)**이어야 한다.

```java
public Runnable createTask(String name) {
    // name은 재할당되지 않으므로 effectively final
    return new Runnable() {
        @Override
        public void run() {
            System.out.println(name + " 작업 실행"); // 바깥 매개변수 캡처
        }
    };
}
```

```java
int counter = 0;
Runnable task = () -> {
    // counter++; // 컴파일 에러 — 캡처된 변수는 재할당 불가 (effectively final 위반)
};
```

메서드가 끝나 지역 변수의 스코프([0-7](../chapter_00_7_scope_casting/) 참고)가 사라져도, 캡처된 값은
익명 클래스/지역 클래스 내부에 복사되어 계속 살아있다.

### 4. 익명 클래스의 실전 활용 — 정렬 기준 즉석 정의

```java
import java.util.*;

List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob"));

names.sort(new Comparator<String>() { // 익명 클래스로 정렬 기준을 즉석에서 정의
    @Override
    public int compare(String a, String b) {
        return a.length() - b.length(); // 문자열 길이 순 정렬
    }
});
```

> 참고: 자바 8부터는 이런 단일 추상 메서드 인터페이스(함수형 인터페이스)의 익명 클래스를
> **람다식**(`(a, b) -> a.length() - b.length()`)으로 훨씬 간결하게 표현할 수 있다. 람다는 이후 심화 과정에서 다룬다.

## 실습 예제

`Validator.java`에서는 익명 클래스로 구현할 인터페이스를 정의한다.

`LocalAnonymousMain.java`에서는 다음을 학습한다:
1. 지역 클래스로 할인 계산 로직을 메서드 안에 캡슐화
2. 익명 클래스로 `Validator` 인터페이스를 즉석 구현
3. 지역 변수 캡처와 effectively final 규칙 확인
4. 익명 클래스로 `Comparator`를 즉석 구현해 정렬 기준 지정

## 주의사항

- 지역 클래스/익명 클래스가 캡처하는 바깥 지역 변수는 반드시 effectively final이어야 한다. 캡처 후 값을 바꾸려 하면 컴파일 에러가 발생한다.
- 익명 클래스는 매번 새로운 `.class` 파일(`Outer$1.class` 형태)을 생성한다. 같은 구현을 여러 번 재사용한다면 이름 있는 클래스나 별도 구현체로 분리하는 것이 낫다.
- 자바 8 이후 함수형 인터페이스(추상 메서드가 1개인 인터페이스)의 익명 클래스는 람다식으로 대체 가능한 경우가 많다. 다만 상태(필드)를 가지거나 여러 메서드를 구현해야 한다면 익명 클래스가 필요하다.

## 다음 챕터

[Chapter 0-20: 예외 처리 1 - 이론](../chapter_00_20_exception_theory/)에서 자바의 예외 계층 구조와 처리 방법을 배운다.
