# Chapter 2-1: 클래스 정의와 인스턴스화

## 학습 목표

- Java 클래스와 생성자를 올바르게 작성한다
- `this` 키워드의 두 가지 용도를 이해한다
- 생성자 오버로딩과 `this()` 체이닝으로 중복을 제거한다
- JDK 16+ `record`를 활용해 불변 데이터 클래스를 만든다

## Python과의 비교

| Python | Java |
|--------|------|
| `class Person:` | `public class Person` |
| `__init__(self, name)` | `public Person(String name)` |
| `self.name = name` | `this.name = name` |
| 생성자 하나만 권장 | 생성자 오버로딩 가능 |
| `Person("홍길동")` | `new Person("홍길동")` |
| 없음 | `record` (불변 데이터 클래스) |

## 핵심 개념

### 1. 클래스 정의

클래스는 객체의 설계도다. 필드(상태)와 메서드(행위)로 구성된다.

```java
public class Person {
    // 필드 (인스턴스 변수)
    private String name;
    private int age;

    // 생성자 — 객체 초기화
    public Person(String name, int age) {
        this.name = name;  // this: 현재 인스턴스 참조
        this.age = age;
    }
}
```

### 2. this 키워드의 두 가지 용도

#### 인스턴스 참조 (`this.필드`)

지역 변수와 필드 이름이 같을 때 필드를 명시한다.

```java
public Person(String name) {
    this.name = name;  // this.name: 필드, name: 파라미터
}
```

#### 생성자 호출 (`this()`)

같은 클래스의 다른 생성자를 호출한다. **반드시 첫 번째 문장**이어야 한다.
중복 초기화 로직을 하나의 생성자에 집중시킨다.

```java
public Person(String name) {
    this(name, 0, "미등록");  // 다른 생성자 위임
}

public Person(String name, int age) {
    this(name, age, "미등록");
}

// 핵심 생성자 — 실제 초기화 로직이 여기에 모임
public Person(String name, int age, String email) {
    this.name = name;
    this.age = age;
    this.email = email;
}
```

### 3. new 연산자와 힙 메모리

`new`는 힙(Heap) 메모리에 객체를 할당하고 생성자를 호출한다.
변수는 객체 자체가 아닌 **참조(주소)**를 저장한다.

```java
Person p1 = new Person("홍길동", 25);
Person p2 = new Person("이몽룡", 30);
Person p3 = p1;  // p1과 같은 객체를 가리킴 (복사 아님)

p3.setName("성춘향");
System.out.println(p1.getName());  // "성춘향" — 같은 객체
```

### 4. record (JDK 16+)

불변(Immutable) 데이터 클래스를 간결하게 선언한다.
컴파일러가 자동으로 생성하는 것들:
- 모든 필드를 받는 생성자
- 각 필드의 accessor 메서드 (getter와 동일하지만 이름이 필드명)
- `equals()`, `hashCode()`, `toString()`

```java
public record Point(int x, int y) {}

Point p = new Point(3, 4);
System.out.println(p.x());       // 3 — accessor
System.out.println(p.toString()); // Point[x=3, y=4]
```

#### compact constructor로 유효성 검사

```java
public record PositivePoint(int x, int y) {
    // compact constructor — 파라미터 목록 생략
    public PositivePoint {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("좌표는 양수여야 합니다");
        }
        // 자동으로 this.x = x; this.y = y; 실행됨
    }
}
```

### 5. 네이밍 컨벤션

| 대상 | 규칙 | 예시 |
|------|------|------|
| 클래스 | PascalCase | `BankAccount`, `OrderItem` |
| 메서드/변수 | camelCase | `getName()`, `accountNumber` |
| 상수 | UPPER_SNAKE_CASE | `MAX_SIZE`, `DEFAULT_TIMEOUT` |
| 패키지 | 소문자 | `org.example.chapter02_1` |

## 실습 예제

`Person.java`에서는 다음을 학습한다:
- 필드, 생성자, getter/setter 기본 구조
- `this()` 생성자 체이닝으로 기본값 처리
- 힙 메모리 참조 동작 확인

`PersonRecord.java`에서는 다음을 학습한다:
- `record`로 불변 데이터 클래스 선언
- compact constructor로 유효성 검사

`ClassInstanceMain.java`에서는 다음을 학습한다:
- 인스턴스 생성과 참조 동작
- `this()` 체이닝 순서 추적
- `record` 사용법

## 주의사항

- `this()`는 반드시 생성자의 **첫 번째 문장**이어야 한다
- `record` 필드는 `private final`로 자동 선언되어 수정 불가
- `record`는 다른 클래스를 `extends`할 수 없다 (`implements`는 가능)
- 생성자 내에서 `this()`와 `super()`를 동시에 호출할 수 없다

## 다음 챕터

[Chapter 2-2: 필드와 메서드](../chapter_02_2_fields_methods/)에서는 static 변수와 정적 팩토리 메서드를 배운다.
