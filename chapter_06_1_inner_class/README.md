# Chapter 6-1: 멤버 내부 클래스

## 학습 목표

- 멤버 내부 클래스(non-static inner class)의 구조와 동작 원리를 이해한다
- 외부 클래스 인스턴스와 내부 클래스의 생명주기 관계를 파악한다
- `Outer.this`를 통해 외부 인스턴스에 접근하는 방법을 익힌다
- 내부 클래스가 적합한 상황과 그렇지 않은 상황을 구분한다

## 핵심 개념

멤버 내부 클래스는 외부 클래스의 인스턴스에 종속된다.
내부 클래스 객체를 생성하려면 반드시 외부 클래스 인스턴스가 먼저 존재해야 한다.

### 1. 기본 구조

```java
public class Outer {
    private String name = "outer";

    public class Inner {
        private String name = "inner";

        public void printNames() {
            System.out.println(name);         // Inner의 name
            System.out.println(Outer.this.name);  // 외부 인스턴스의 name
        }
    }
}
```

```java
// 내부 클래스 인스턴스 생성 — 외부 인스턴스가 필요
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

### 2. 외부 인스턴스 참조

내부 클래스는 외부 클래스의 `private` 멤버에도 직접 접근할 수 있다.
이름 충돌 시 `Outer.this.필드명`으로 외부 인스턴스를 명시적으로 참조한다.

```java
public class Counter {
    private int count = 0;

    public class Incrementer {
        public void increment() {
            count++;  // 외부 클래스의 private 필드에 직접 접근
        }
    }
}
```

### 3. 위반 예시 — public 내부 클래스 남용

```java
// 나쁜 설계 — 외부와 강결합된 public 내부 클래스
public class Order {
    public class Item {  // public이면 외부에서 직접 생성 가능 → 의존 관계가 복잡해짐
        public String name;
    }
}

// 호출 측이 Order의 구조에 강하게 결합된다
Order order = new Order();
Order.Item item = order.new Item();
```

### 4. 개선 — 내부 클래스는 외부 클래스가 직접 관리

```java
public class Order {
    private List<Item> items = new ArrayList<>();

    // private — 외부에서 직접 생성 불가, Order가 생명주기 관리
    private class Item {
        private final String name;
        Item(String name) { this.name = name; }
    }

    public void addItem(String name) {
        items.add(new Item(name));  // Order만 Item을 생성한다
    }
}
```

## 실습 예제

`Outer.java`에서는 다음을 학습한다:
- 멤버 내부 클래스 기본 구조
- `Outer.this`를 통한 외부 인스턴스 참조
- 외부 클래스의 `private` 멤버 접근

`EventButton.java`에서는 다음을 학습한다:
- 실전 패턴: 이벤트 리스너를 내부 클래스로 구현
- 내부 클래스가 외부 클래스 상태에 자연스럽게 접근하는 방식

`InnerClassMain.java`에서는 다음을 학습한다:
- public 내부 클래스 남용 위반 예시
- 내부 클래스 인스턴스 생성 방법
- 이벤트 버튼 시연

## 주의사항

- 멤버 내부 클래스는 외부 인스턴스에 대한 숨겨진 참조를 항상 보유한다 → 외부 객체가 GC되지 않는 메모리 누수 위험
- 직렬화(Serialization)가 필요한 클래스에는 내부 클래스 사용을 피한다
- 외부 인스턴스가 필요 없다면 `static` 중첩 클래스를 사용하는 것이 더 적합하다

## 다음 챕터

[Chapter 6-2: 정적 중첩 클래스](../chapter_06_2_static_nested/)에서는 외부 인스턴스가 불필요한 static nested class를 배운다.
