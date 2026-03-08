# Chapter 2-4: Builder 패턴과 DSL

## 학습 목표

- Telescoping Constructor 안티패턴의 문제를 이해한다
- Builder 패턴을 직접 구현하고 동작 원리를 이해한다
- DSL(Domain Specific Language)과 Fluent Interface 개념을 이해한다
- Lombok `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor`, `@RequiredArgsConstructor`를 활용한다

## 핵심 개념

### 1. Telescoping Constructor 안티패턴

파라미터가 늘어날수록 생성자가 기하급수적으로 늘어난다.

```java
// 어떤 파라미터가 무엇인지 호출부에서 알 수 없다
new Order("홍길동", "서울", null, true, false, 3000, "CARD");
//          이름?    주소?   ?     ?     ?    배달비?   결제?
```

### 2. Builder 패턴 직접 구현

내부 static 클래스로 Builder를 만들고, 각 setter가 `this`를 반환해 **메서드 체이닝**을 지원한다.

```java
public class Person {
    private final String name;
    private final int age;
    private final String email;

    private Person(Builder builder) {
        this.name  = builder.name;
        this.age   = builder.age;
        this.email = builder.email;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String name;
        private int age;
        private String email = "";  // 기본값

        public Builder name(String name)   { this.name = name;   return this; }
        public Builder age(int age)        { this.age = age;     return this; }
        public Builder email(String email) { this.email = email; return this; }

        public Person build() {
            if (name == null) throw new IllegalStateException("이름은 필수입니다");
            return new Person(this);
        }
    }
}

// 사용
Person p = Person.builder()
    .name("홍길동")
    .age(25)
    .email("hong@example.com")
    .build();
```

### 3. DSL (Domain Specific Language)

도메인 언어처럼 읽히는 코드 설계를 의미한다.
메서드 이름이 마치 문장처럼 읽힌다.

```java
// Query DSL 스타일
String query = QueryBuilder.select("name", "email")
    .from("users")
    .where("age > 18")
    .orderBy("name")
    .limit(10)
    .build();
// SELECT name, email FROM users WHERE age > 18 ORDER BY name LIMIT 10
```

### 4. Fluent Interface

메서드 체이닝을 통해 선언적이고 읽기 쉬운 코드를 작성하는 스타일이다.
Builder 패턴은 Fluent Interface의 대표적 구현이다.

### 5. Lombok 생성자 어노테이션 비교

| 어노테이션 | 생성하는 생성자 | 포함 필드 |
|------------|----------------|-----------|
| `@AllArgsConstructor` | 모든 필드를 파라미터로 받는 생성자 | 모든 필드 |
| `@NoArgsConstructor` | 파라미터 없는 기본 생성자 | (없음) |
| `@RequiredArgsConstructor` | `final` 필드와 `@NonNull` 필드만 받는 생성자 | final + @NonNull |

```java
@RequiredArgsConstructor  // final 필드만 생성자에 포함
public class OrderService {
    private final OrderRepository repository;  // 생성자에 포함
    private final EmailNotifier notifier;      // 생성자에 포함
    private String tempData;                   // 제외
}
```

### 6. Lombok @Builder

`@Builder`를 붙이면 Builder 내부 클래스를 자동으로 생성한다.

```java
@Builder
public class Person {
    private String name;
    private int age;
    @Builder.Default
    private String role = "USER";  // 기본값
}
```

`@Singular`: 컬렉션 필드에 단수 이름으로 추가하는 메서드를 생성한다.
```java
@Builder
public class Team {
    @Singular
    private List<String> members;  // team.member("홍길동") 형태로 추가
}
```

## 실습 예제

`PersonBuilder.java`에서는 다음을 학습한다:
- Builder 패턴 직접 구현
- `@Builder`, `@Builder.Default`, `@Singular` 사용

`QueryBuilder.java`에서는 다음을 학습한다:
- DSL 스타일 Builder로 SQL 쿼리 문자열 생성
- Fluent Interface 패턴

`BuilderMain.java`에서는 다음을 학습한다:
- 각 생성 방식 비교 실행
- Lombok 어노테이션 차이 확인

## 주의사항

- `@Builder`와 `@AllArgsConstructor`를 동시에 사용할 때는 생성자 충돌을 주의한다
- `@Builder`는 기본적으로 모든 필드를 Builder에 포함시킨다 — `@Builder.Default`로 기본값 설정
- DSL은 읽기 좋게 설계되어야 한다 — 메서드 이름이 자연어처럼 읽혀야 함

## 다음 챕터

[Chapter 2-5: 열거형 (Enum)](../chapter_02_5_enum/)에서는 Java의 강력한 enum을 배운다.
