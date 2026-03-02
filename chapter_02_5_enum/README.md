# Chapter 2-5: 열거형 (Enum)

## 학습 목표

- Java `enum`이 단순 상수 집합이 아닌 완전한 클래스임을 이해한다
- 필드, 생성자, 메서드를 가진 enum을 작성한다
- enum 내 추상 메서드로 상수별 행위를 다르게 구현한다
- `EnumSet`, `EnumMap`의 성능 특성을 이해한다
- switch expression과 enum을 함께 사용한다

## Python과의 비교

| Python | Java |
|--------|------|
| `from enum import Enum` | `enum` 키워드 (별도 import 불필요) |
| `Color.RED.value` | `Direction.NORTH.getAngle()` (커스텀 필드) |
| `for e in MyEnum` | `MyEnum.values()` |
| 기본 클래스 | 완전한 클래스 (필드, 메서드, 인터페이스 구현) |

## 핵심 개념

### 1. Java enum은 클래스다

`enum`은 컴파일 시 `java.lang.Enum`을 상속하는 클래스로 변환된다.
각 상수는 해당 enum 타입의 `static final` 인스턴스다.

```java
// 컴파일 후 실질적으로 이런 구조
public final class Direction extends Enum<Direction> {
    public static final Direction NORTH = new Direction("NORTH", 0);
    public static final Direction SOUTH = new Direction("SOUTH", 1);
    // ...
}
```

### 2. 필드와 생성자를 가진 enum

```java
public enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS  (4.869e+24, 6.0518e6),
    EARTH  (5.976e+24, 6.37814e6);

    private final double mass;    // kg
    private final double radius;  // m

    // enum 생성자는 항상 private (암묵적으로)
    Planet(double mass, double radius) {
        this.mass   = mass;
        this.radius = radius;
    }

    double surfaceGravity() {
        final double G = 6.67300E-11;
        return G * mass / (radius * radius);
    }
}
```

### 3. enum 내 추상 메서드 — 상수별 다른 구현

```java
public enum Operation {
    PLUS("+")  { @Override public int apply(int x, int y) { return x + y; } },
    MINUS("-") { @Override public int apply(int x, int y) { return x - y; } },
    TIMES("*") { @Override public int apply(int x, int y) { return x * y; } };

    private final String symbol;
    Operation(String symbol) { this.symbol = symbol; }

    public abstract int apply(int x, int y);
}
```

### 4. EnumSet과 EnumMap

일반 `HashSet<MyEnum>`, `HashMap<MyEnum, V>` 대신 `EnumSet`, `EnumMap`을 사용하면 **비트 벡터** 기반으로 구현되어 훨씬 빠르다.

```java
EnumSet<Direction> cardinalDirections = EnumSet.of(Direction.NORTH, Direction.SOUTH);
EnumMap<Direction, String> labels = new EnumMap<>(Direction.class);
labels.put(Direction.NORTH, "북");
```

### 5. switch expression과 enum (JDK 14+)

```java
String label = switch (direction) {
    case NORTH -> "북";
    case SOUTH -> "남";
    case EAST  -> "동";
    case WEST  -> "서";
};
// 모든 상수를 처리하지 않으면 컴파일 에러 — 안전한 완전성 검사
```

### 6. 유용한 enum 내장 메서드

| 메서드 | 설명 |
|--------|------|
| `values()` | 모든 상수 배열 반환 |
| `valueOf(String)` | 이름으로 상수 반환 |
| `ordinal()` | 선언 순서 (0부터) — DB 저장 시 사용 주의 |
| `name()` | 상수 이름 문자열 반환 |

## 실습 예제

`Direction.java`에서는 다음을 학습한다:
- 필드(angle)와 메서드를 가진 enum
- switch expression 연동

`Planet.java`에서는 다음을 학습한다:
- 복잡한 계산 로직이 포함된 enum

`OrderStatus.java`에서는 다음을 학습한다:
- 추상 메서드로 상수별 다른 행위 구현
- 상태 전이 검증 (현재 상태에서 전환 가능한 상태 목록)
- EnumSet 활용

`EnumMain.java`에서는 다음을 학습한다:
- EnumSet, EnumMap 성능 특성
- values(), valueOf() 활용

## 주의사항

- `ordinal()`은 DB에 저장하는 용도로 사용 금지 — 순서 변경 시 의미가 깨짐
- DB 저장 시 `name()` 또는 커스텀 코드 필드를 사용한다
- `enum`은 다른 클래스를 `extends`할 수 없다 (이미 `Enum`을 상속하므로)
- `enum`은 인스턴스 직접 생성 불가 (`new MyEnum()` 불가)

## 다음 챕터

[Chapter 3-1: 접근 제어자](../chapter_03_1_access_control/)에서는 캡슐화와 접근 제어를 배운다.
