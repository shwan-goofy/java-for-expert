# Chapter 6-2: 정적 중첩 클래스

## 학습 목표

- 정적 중첩 클래스(static nested class)와 멤버 내부 클래스의 차이를 이해한다
- 외부 인스턴스 참조가 없는 정적 중첩 클래스의 메모리 특성을 파악한다
- Builder 패턴과 자료구조 노드에 정적 중첩 클래스를 적용한다
- 두 유형 중 어느 쪽을 선택해야 하는지 기준을 세운다

## 핵심 개념

정적 중첩 클래스는 외부 클래스 인스턴스 없이 독립적으로 생성할 수 있다.
외부 인스턴스에 대한 숨겨진 참조를 보유하지 않아 메모리 누수 위험이 없다.

### 1. 멤버 내부 클래스 vs 정적 중첩 클래스

```java
public class Outer {

    // 멤버 내부 클래스 — Outer 인스턴스 없이 생성 불가
    public class Inner {
        void show() { /* Outer.this 참조 보유 */ }
    }

    // 정적 중첩 클래스 — Outer 인스턴스 없이 생성 가능
    public static class Nested {
        void show() { /* Outer.this 참조 없음 */ }
    }
}

// 멤버 내부 클래스: 외부 인스턴스 필요
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();

// 정적 중첩 클래스: 외부 인스턴스 불필요
Outer.Nested nested = new Outer.Nested();
```

### 2. Builder 패턴 — 정적 중첩 클래스의 대표 용도

```java
public class HttpRequest {
    private final String url;
    private final String method;

    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
    }

    public static class Builder {
        private String url;
        private String method = "GET";

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}

// 사용
HttpRequest request = new HttpRequest.Builder()
    .url("https://api.example.com")
    .build();
```

Builder는 외부 클래스(HttpRequest)의 인스턴스 상태가 아닌 **타입 정보**만 필요하므로
`static`으로 선언하는 것이 적합하다.

### 3. 자료구조 노드 — 외부 인스턴스가 필요 없는 경우

```java
public class LinkedList<T> {

    private Node<T> head;

    // Node는 LinkedList 인스턴스와 무관하게 독립적으로 존재
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }
}
```

`Node`가 `static`이 아닌 내부 클래스라면 모든 노드가 `LinkedList` 인스턴스를 참조해
불필요한 메모리를 낭비한다.

### 4. 위반 예시 — static이 적합한데 non-static 사용

```java
// 나쁜 설계 — Node가 LinkedList 인스턴스를 불필요하게 참조
public class LinkedList<T> {
    private Node<T> head;

    class Node<T> {  // static 없음 → 모든 Node가 LinkedList.this를 보유
        T data;
        Node<T> next;
    }
}
```

## 실습 예제

`LinkedList.java`에서는 다음을 학습한다:
- `static class Node<T>` — 리스트 인스턴스와 분리된 독립 노드
- non-static Node와의 메모리 차이 비교 주석

`HttpRequest.java`에서는 다음을 학습한다:
- `static class Builder` — Builder 패턴을 정적 중첩 클래스로 구현
- private 생성자와 Builder를 통한 객체 생성 강제

`StaticNestedMain.java`에서는 다음을 학습한다:
- non-static vs static 생성 방식 비교
- LinkedList와 HttpRequest.Builder 동작 시연

## 주의사항

- 외부 인스턴스 상태가 필요 없다면 항상 `static` 중첩 클래스를 사용한다
- 정적 중첩 클래스는 외부 클래스의 `static` 멤버에는 접근할 수 있지만 인스턴스 멤버에는 접근할 수 없다
- Effective Java Item 24: "멤버 클래스가 바깥 인스턴스에 접근할 일이 없다면 무조건 static으로 만들어라"

## 다음 챕터

[Chapter 6-3: 지역 클래스와 익명 클래스](../chapter_06_3_anonymous/)에서는 메서드 안에 선언하는 클래스와 이름 없는 클래스를 배운다.
