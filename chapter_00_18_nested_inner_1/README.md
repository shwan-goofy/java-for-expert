# Chapter 0-18: 중첩 클래스, 내부 클래스 1

## 학습 목표

- 클래스 안에 클래스를 선언하는 이유(연관 관계가 강한 클래스를 묶어서 캡슐화)를 이해한다
- **static 중첩 클래스**와 **인스턴스(비static) 내부 클래스**의 차이를 이해한다
- 내부 클래스가 바깥 클래스의 인스턴스에 종속되는 구조를 이해한다

## Python과의 비교

| Python | Java |
|---|---|
| 클래스 안에 클래스 선언 가능하지만 관용적으로 드묾 | 클래스 안에 클래스를 선언하는 것이 일반적인 패턴 |
| 중첩 클래스도 외부 인스턴스와 자동으로 연결되지 않음 | static 중첩 클래스는 외부 인스턴스 불필요, 인스턴스 내부 클래스는 외부 인스턴스 필요 |
| 내부 클래스 개념 구분이 크게 없음 | static 중첩 / 인스턴스 내부 / 지역 / 익명, 4가지로 명확히 구분 ([0-19](../chapter_00_19_nested_inner_2/)에서 나머지 2개를 다룸) |

## 핵심 개념

### 1. 왜 클래스 안에 클래스를 선언할까?

두 클래스가 **개념적으로 강하게 연관되어 있고, 다른 곳에서는 거의 재사용되지 않을 때** 중첩 클래스로 묶으면
캡슐화가 좋아지고 패키지가 불필요한 클래스로 지저분해지는 것을 막을 수 있다.

```java
public class LinkedList {
    private Node head; // Node는 LinkedList 내부에서만 의미가 있는 개념

    private static class Node {
        int value;
        Node next;
    }
}
```

### 2. static 중첩 클래스 (Static Nested Class)

`static`이 붙은 중첩 클래스는 **바깥 클래스의 인스턴스 없이도** 만들 수 있다.
바깥 클래스의 인스턴스 필드에 접근할 수 없다(당연히 인스턴스가 없을 수도 있으므로).

```java
public class HttpRequest {
    private final String url;

    public HttpRequest(String url) {
        this.url = url;
    }

    // static 중첩 클래스 — HttpRequest 인스턴스 없이 생성 가능
    public static class Builder {
        private String url;
        private String method = "GET";

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(url);
        }
    }
}

// 바깥 인스턴스 없이 바로 생성
HttpRequest.Builder builder = new HttpRequest.Builder();
HttpRequest request = builder.url("https://example.com").method("POST").build();
```

빌더 패턴처럼 **바깥 클래스와 밀접히 연관되지만 독립적으로 존재해야 하는 구조**에 적합하다.

### 3. 인스턴스 내부 클래스 (Non-static Inner Class)

`static`이 없는 내부 클래스는 **반드시 바깥 클래스의 인스턴스가 있어야만** 생성할 수 있다.
바깥 클래스의 인스턴스 필드(`private` 포함)에 자유롭게 접근할 수 있다.

```java
public class Counter {
    private int count = 0;

    public class Incrementer { // 인스턴스 내부 클래스 — Counter 인스턴스에 종속
        public void increment() {
            count++; // 바깥 클래스의 private 필드에 직접 접근
        }
    }
}

Counter counter = new Counter();
Counter.Incrementer incrementer = counter.new Incrementer(); // 바깥 인스턴스.new 내부클래스()
incrementer.increment();
```

### 4. static 중첩 클래스 vs 인스턴스 내부 클래스, 언제 선택할까?

| 구분 | static 중첩 클래스 | 인스턴스 내부 클래스 |
|---|---|---|
| 바깥 인스턴스 필요 여부 | 불필요 | 필요 (`outer.new Inner()`) |
| 바깥 인스턴스 필드 접근 | 불가능 | 가능 |
| 생성 방법 | `new Outer.Nested()` | `outer.new Inner()` |
| 대표 사용처 | Builder, 자료구조의 Node | 바깥 객체의 상태에 강하게 의존하는 헬퍼 |

**바깥 인스턴스의 상태에 접근할 필요가 없다면 항상 static 중첩 클래스를 우선 고려한다.**
불필요하게 인스턴스 내부 클래스를 사용하면 바깥 인스턴스에 대한 숨은 참조가 생겨 메모리 누수 위험이 커진다.

## 실습 예제

`HttpRequest.java`에서는 static 중첩 클래스로 빌더 패턴을 구현하는 방법을 학습한다.

`Counter.java`에서는 인스턴스 내부 클래스가 바깥 클래스의 `private` 필드에 접근하는 방법을 학습한다.

`NestedInnerMain.java`에서는 두 클래스를 실제로 생성하고 사용하는 방법을 비교한다.

## 주의사항

- static 중첩 클래스는 `new Outer.Nested()`처럼 바깥 인스턴스 없이 생성한다. 인스턴스 내부 클래스는 반드시 `outer.new Inner()` 형태로 생성해야 한다.
- 인스턴스 내부 클래스는 바깥 인스턴스에 대한 참조를 암묵적으로 갖고 있어, 내부 클래스 객체가 살아있는 동안 바깥 객체도 GC(가비지 컬렉션)되지 않는다.
- 더 깊은 예시(이벤트 리스너 패턴 등)는 이 저장소의 [chapter_06_1_inner_class](../chapter_06_1_inner_class/), [chapter_06_2_static_nested](../chapter_06_2_static_nested/)에서 이어서 다룬다.

## 다음 챕터

[Chapter 0-19: 중첩 클래스, 내부 클래스 2](../chapter_00_19_nested_inner_2/)에서 지역 클래스와 익명 클래스를 배운다.
