# Chapter 4-2: 합성과 집약

## 학습 목표

- 합성(Composition)과 집약(Aggregation)의 생명주기 차이를 이해한다
- 상속의 오용 사례를 인식하고 합성으로 대체한다
- 위임(Delegation) 패턴을 구현한다
- "상속보다 합성을 선호하라" 원칙의 의미를 이해한다

## Python과의 비교

| Python | Java |
|--------|------|
| 합성/집약 (동일한 문법) | 합성/집약 (동일한 문법) |
| `self.engine = Engine()` | `this.engine = new Engine()` |
| 다중 상속으로 해결 가능 | 단일 상속 → 합성으로 해결 |

## 핵심 개념

### 1. 합성 (Composition) — 강한 Has-a

포함된 객체의 **생명주기가 포함하는 객체에 종속**된다.
포함하는 객체가 소멸되면 포함된 객체도 소멸된다.

```java
public class Car {
    // Engine은 Car 안에서 생성 — Car가 없으면 Engine도 없음
    private final Engine engine = new Engine(2000);

    public void start() {
        engine.start();  // 위임
    }
}
```

### 2. 집약 (Aggregation) — 약한 Has-a

포함된 객체가 **독립적으로 존재**할 수 있다.
포함하는 객체가 소멸되어도 포함된 객체는 살아있다.

```java
public class Department {
    private final List<Employee> employees;

    // Employee는 외부에서 주입 — Department가 사라져도 Employee는 독립 존재
    public Department(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
    }
}
```

### 3. 상속의 오용 — 안티패턴

`Stack extends Vector`는 Java 표준 라이브러리의 유명한 설계 실수다.
`Stack`이 `Vector`를 상속하면 `push`/`pop` 외에 `Vector`의 `add(index, element)`, `set()` 등 모든 메서드가 노출되어 스택의 LIFO 불변식이 깨진다.

```java
// 잘못된 설계 — Stack은 Vector가 아님 (Is-a 관계 X)
// java.util.Stack<E> extends Vector<E>  ← 안티패턴

Stack<String> stack = new Stack<>();
stack.push("first");
stack.push("second");
stack.add(0, "injected");  // Vector의 메서드 — 스택 불변식 파괴
```

### 4. 위임 (Delegation) 패턴

상속 대신 합성으로 기능을 재사용한다.
내부 객체의 메서드를 호출하는 방식으로 행위를 위임한다.

```java
// 상속 대신 합성 + 위임
public class MyStack<T> {
    private final List<T> storage = new ArrayList<>();

    public void push(T item) { storage.add(item); }
    public T pop() {
        if (storage.isEmpty()) throw new NoSuchElementException();
        return storage.remove(storage.size() - 1);
    }
    // add(index, element) 등 불필요한 메서드 노출 없음
}
```

### 5. 합성이 상속보다 나은 경우

- **유연성**: 런타임에 구현체를 교체할 수 있다
- **캡슐화 유지**: 부모의 구현 변경이 자식에 전파되지 않는다
- **단일 책임**: 관심사를 작은 클래스로 분리할 수 있다

## 실습 예제

`Engine.java`, `Car.java`에서는 다음을 학습한다:
- 합성 — Car가 Engine을 내부 생성 (생명주기 종속)

`Employee.java`, `Department.java`에서는 다음을 학습한다:
- 집약 — Department가 Employee를 외부에서 주입 (독립 생존)

`CompositionMain.java`에서는 다음을 학습한다:
- Stack 안티패턴 시연
- 합성 기반 안전한 스택 구현

## 주의사항

- Is-a 관계가 아니라면 상속을 사용하지 않는다
- 상속 계층이 3단계 이상이면 합성으로 재설계를 고려한다
- 합성을 사용하면 코드가 다소 장황해지지만 유연성과 캡슐화에서 이점이 크다

## 다음 챕터

[Chapter 4-3: 추상 클래스와 인터페이스](../chapter_04_3_abstract/)에서는 추상화 메커니즘을 배운다.
