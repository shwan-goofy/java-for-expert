# Chapter 6-3: 지역 클래스와 익명 클래스

## 학습 목표

- 지역 클래스(local class)의 선언 위치와 스코프를 이해한다
- 익명 클래스(anonymous class)로 인터페이스와 추상 클래스를 즉석에서 구현한다
- 익명 클래스와 람다 표현식의 관계 및 전환 조건을 파악한다
- 각 유형이 캡처(capture)하는 변수의 규칙을 이해한다

## 핵심 개념

### 1. 지역 클래스 (Local Class)

메서드 **안에** 선언하는 클래스다. 해당 메서드 스코프 안에서만 존재하며
메서드의 `final` 또는 effectively final 지역 변수를 캡처할 수 있다.

```java
public void process(String prefix) {
    // 지역 클래스 — 메서드 안에 선언
    class Formatter {
        String format(String value) {
            return prefix + ": " + value;  // effectively final 변수 캡처
        }
    }

    Formatter formatter = new Formatter();
    System.out.println(formatter.format("hello"));
}
```

### 2. 익명 클래스 (Anonymous Class)

이름 없이 인터페이스나 추상 클래스를 구현하는 일회성 클래스다.
선언과 동시에 인스턴스가 생성된다.

```java
// 인터페이스를 익명 클래스로 즉석 구현
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("익명 클래스로 구현한 run()");
    }
};
r.run();
```

### 3. 위반 예시 — 람다로 대체 가능한데 익명 클래스 사용

```java
// 나쁜 설계 — 함수형 인터페이스를 익명 클래스로 구현 (불필요하게 장황)
Comparator<String> comparator = new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.length() - b.length();
    }
};
```

### 4. 개선 — 함수형 인터페이스는 람다로 대체

```java
// 추상 메서드가 하나인 함수형 인터페이스 → 람다 사용
Comparator<String> comparator = (a, b) -> a.length() - b.length();
```

익명 클래스가 여전히 적합한 경우:
- 추상 메서드가 **두 개 이상**인 인터페이스나 추상 클래스 구현
- 상태(필드)를 가지는 구현이 필요할 때
- `this`가 익명 클래스 자신을 가리켜야 할 때 (람다에서 `this`는 바깥 클래스)

### 5. 변수 캡처 규칙

```java
int count = 0;  // effectively final — 익명 클래스/람다가 캡처 가능

Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println(count);  // 캡처 OK
        // count++;  // 컴파일 오류 — 캡처된 변수는 수정 불가
    }
};
```

## 실습 예제

`Validator.java`에서는 다음을 학습한다:
- 추상 메서드가 두 개인 인터페이스 — 익명 클래스로만 구현 가능 (람다 불가)
- 추상 메서드가 하나인 `@FunctionalInterface` — 람다 전환 가능

`SortDemo.java`에서는 다음을 학습한다:
- `Comparator` 익명 클래스 구현
- 동일 로직을 람다, 메서드 참조로 단계적으로 단순화

`AnonymousMain.java`에서는 다음을 학습한다:
- 지역 클래스 선언과 변수 캡처 시연
- 익명 클래스 생성 예시
- 람다 전환 비교 및 `this` 참조 차이 시연

## 주의사항

- 지역 클래스와 익명 클래스는 캡처 변수가 반드시 `final` 또는 effectively final이어야 한다
- 함수형 인터페이스(`@FunctionalInterface`)는 람다로 대체하는 것을 우선 고려한다
- 익명 클래스 안의 `this`는 **익명 클래스 자신**을 가리킨다; 람다 안의 `this`는 **바깥 클래스**를 가리킨다
- 익명 클래스는 생성자를 정의할 수 없다 (인스턴스 초기화 블록 `{}` 사용)

## 다음 챕터

[Chapter 6-4]가 추가될 경우 이곳에 링크를 연결한다.  
현재는 Chapter 6의 마지막 챕터다.
