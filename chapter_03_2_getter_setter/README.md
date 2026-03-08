# Chapter 3-2: Getter/Setter와 Lombok, Record

## 학습 목표

- Java getter/setter 네이밍 관례를 이해한다
- setter 내 유효성 검사로 불변식을 보호한다
- Lombok `@Getter`, `@Setter`, `@Data`로 보일러플레이트를 제거한다
- `record`의 compact constructor와 accessor 메서드를 이해한다
- `sealed class`의 개념을 소개한다

## Python과의 비교

| Python | Java |
|--------|------|
| `@property` | getter 메서드 (`getXxx()`) |
| `@x.setter` | setter 메서드 (`setXxx(value)`) |
| `@x.deleter` | (직접 지원 없음) |
| `@property`로 유효성 검사 | setter 내에서 유효성 검사 |
| `@dataclass` | Lombok `@Data` 또는 `record` |

## 핵심 개념

### 1. Getter/Setter 네이밍 관례

JavaBeans 규약을 따른다. 프레임워크(Spring, JPA 등)가 이 관례에 의존한다.

```java
// getter — get + 필드명(첫 글자 대문자)
public String getName() { return name; }

// boolean getter — is + 필드명
public boolean isActive() { return active; }

// setter — set + 필드명
public void setName(String name) { this.name = name; }
```

### 2. setter 내 유효성 검사

setter에서 유효하지 않은 값을 거부해 객체를 항상 유효한 상태로 유지한다.

```java
public void setTemperature(double value) {
    if (value < -273.15) {
        throw new IllegalArgumentException("절대 영도 이하는 불가: " + value);
    }
    this.temperature = value;
}
```

### 3. Lombok @Getter / @Setter

```java
import lombok.Getter;
import lombok.Setter;

@Getter          // 모든 필드에 getter 생성
@Setter          // 모든 필드에 setter 생성
public class Member {
    private String name;
    private int age;

    @Setter(AccessLevel.NONE)  // 특정 필드만 setter 제외
    private String id;
}
```

### 4. Lombok @Data

`@Getter` + `@Setter` + `@ToString` + `@EqualsAndHashCode` + `@RequiredArgsConstructor`를 한 번에 적용한다.

```java
@Data
public class Member {
    private String name;
    private int age;
}
```

#### @Data 사용 시 주의사항 — JPA Entity에 금지

JPA `@Entity`에 `@Data`를 사용하면 안 되는 이유:
1. `@EqualsAndHashCode`가 `id`를 포함해 해시코드를 계산 — 영속화 전(id=null)과 후(id=1) 해시코드가 달라 `HashSet` 동작 이상
2. `@ToString`이 양방향 관계를 순환 호출해 `StackOverflowError` 발생 가능
3. `@Setter`가 모든 필드를 노출해 불변 id 변경 가능

### 5. record의 accessor

`record`의 getter는 get 접두사 없이 필드명만 사용한다.

```java
record Temperature(double celsius) {
    // compact constructor — 유효성 검사
    Temperature {
        if (celsius < -273.15) throw new IllegalArgumentException("...");
    }
}

Temperature t = new Temperature(100.0);
t.celsius();  // 100.0 — get 없이 필드명으로 접근
```

### 6. sealed class (JDK 17)

허용된 서브클래스만 상속할 수 있는 클래스다.
계층 구조를 명시적으로 제한하여 switch expression의 완전성을 보장한다.

```java
public sealed class Shape permits Circle, Rectangle, Triangle {}

public final class Circle extends Shape {
    private final double radius;
    // ...
}

// switch에서 모든 허용 서브타입을 처리하지 않으면 컴파일 에러
double area = switch (shape) {
    case Circle c    -> Math.PI * c.radius() * c.radius();
    case Rectangle r -> r.width() * r.height();
    case Triangle t  -> 0.5 * t.base() * t.height();
};
```

## 실습 예제

`Temperature.java`에서는 다음을 학습한다:
- setter에서 절대 영도 유효성 검사
- 섭씨/화씨 변환 메서드

`TemperatureRecord.java`에서는 다음을 학습한다:
- `record`로 불변 온도 클래스 재구현
- compact constructor 유효성 검사

`MemberLombok.java`에서는 다음을 학습한다:
- `@Getter`, `@Setter`, `@Data` 사용
- `@Data` 남용 위험 시연

`GetterSetterMain.java`에서는 다음을 학습한다:
- 세 가지 방식 비교 실행
- sealed class 활용

## 주의사항

- 무분별한 `@Data` 사용은 캡슐화를 파괴한다 — 필요한 접근자만 선택적으로 생성한다
- `record`는 불변 데이터 전달 객체(DTO)에 적합하다
- setter가 있다면 항상 유효성 검사를 포함한다

## 다음 챕터

[Chapter 4-1: 상속](../chapter_04_1_inheritance/)에서는 클래스 간 상속 관계를 배운다.
