# Chapter 0-16: 열거형 - ENUM

## 학습 목표

- `enum`이 왜 정수 상수보다 안전한지 이해한다
- `enum`에 필드와 메서드를 추가해 각 상수가 고유한 데이터/동작을 갖게 하는 방법을 익힌다
- 상수마다 다른 동작이 필요할 때 추상 메서드를 구현하는 패턴을 익힌다
- `values()`, `valueOf()`, `ordinal()`, `name()` 등 enum의 기본 메서드를 사용한다

## Python과의 비교

| Python | Java |
|---|---|
| `class Color(Enum): RED = 1` | `enum Color { RED, BLUE, GREEN }` |
| Enum 멤버마다 값(value) 지정 가능 | 상수마다 생성자 인자로 필드 값 지정 가능 |
| 정수 상수만 쓰는 경우도 흔함 (타입 안전성 약함) | `enum` 자체가 타입이므로 잘못된 값 대입이 컴파일 에러 |
| 멤버별 동작 오버라이드는 다소 번거로움 | 상수별로 추상 메서드를 즉석에서 구현 가능 |

## 핵심 개념

### 1. enum이 필요한 이유 — 정수 상수의 문제

```java
// 정수 상수 방식 — 타입 안전성이 없음
public static final int SEASON_SPRING = 0;
public static final int SEASON_SUMMER = 1;

int season = 5;         // 컴파일러가 막지 못하는 잘못된 값
if (season == SEASON_SPRING) { ... }
```

`int` 상수는 실수로 잘못된 숫자를 넣어도 컴파일러가 잡아내지 못한다. `enum`은 **정해진 값만 존재하는 새로운 타입**을
만들어 이 문제를 해결한다.

```java
public enum Season { SPRING, SUMMER, FALL, WINTER }

Season season = Season.SPRING; // Season 타입에는 이 4개 값만 존재할 수 있다
```

### 2. enum의 기본 메서드

```java
Season season = Season.SUMMER;

season.name();      // "SUMMER" — 선언된 이름 그대로
season.ordinal();   // 1 — 선언 순서 (0부터 시작), 순서가 바뀌면 값도 바뀌므로 저장용으로는 비권장
Season.values();    // 모든 상수를 배열로 반환: [SPRING, SUMMER, FALL, WINTER]
Season.valueOf("WINTER"); // 문자열로 enum 상수 찾기 -> Season.WINTER
```

### 3. 필드와 생성자를 가진 enum

`enum`도 클래스이므로 필드, 생성자, 메서드를 가질 수 있다. 단, **생성자는 항상 `private`**이며
(다른 곳에서 `new Season(...)`으로 만들 수 없음) 상수를 선언할 때 자동으로 호출된다.

```java
public enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    EARTH(5.976e+24, 6.37814e6),
    JUPITER(1.9e+27, 7.1492e7);

    private final double mass;   // kg
    private final double radius; // m

    Planet(double mass, double radius) { // enum 생성자는 항상 private (묵시적)
        this.mass = mass;
        this.radius = radius;
    }

    public double surfaceGravity() {
        final double G = 6.67300E-11;
        return G * mass / (radius * radius);
    }
}

double gravity = Planet.EARTH.surfaceGravity();
```

### 4. 상수마다 다른 동작 — 추상 메서드 구현

`enum`에 추상 메서드를 선언하고, 각 상수가 자신만의 구현을 몸체로 가질 수 있다.
`if-else`나 `switch`로 상수별 분기를 하는 대신, 각 상수가 스스로 동작을 책임지게 하는 방식이다.

```java
public enum Operation {
    PLUS {
        @Override
        public int apply(int a, int b) { return a + b; }
    },
    MINUS {
        @Override
        public int apply(int a, int b) { return a - b; }
    },
    MULTIPLY {
        @Override
        public int apply(int a, int b) { return a * b; }
    };

    public abstract int apply(int a, int b); // 각 상수가 반드시 구현해야 하는 추상 메서드
}

int result = Operation.PLUS.apply(3, 4); // 7
```

### 5. enum과 switch

`enum`은 `switch`문/식과 함께 자주 사용된다.

```java
Season season = Season.SUMMER;
String message = switch (season) {
    case SPRING -> "따뜻해요";
    case SUMMER -> "더워요";
    case FALL -> "선선해요";
    case WINTER -> "추워요";
};
```

## 실습 예제

`Season.java`에서는 기본 enum과 `values()`, `valueOf()`, `ordinal()`, `name()` 활용을 학습한다.

`Planet.java`에서는 필드와 생성자를 가진 enum, 상수별 계산 메서드(`surfaceGravity()`)를 학습한다.

`Operation.java`에서는 상수마다 다른 동작을 구현하는 추상 메서드 패턴을 학습한다.

`EnumMain.java`에서는 위 세 enum을 활용해 실행 결과를 확인한다.

## 주의사항

- `ordinal()`은 선언 순서에 의존하므로, 중간에 상수 순서를 바꾸거나 새 상수를 끼워 넣으면 값이 바뀐다. **데이터베이스 저장 등 영속적인 값으로는 사용하지 않는다.** 대신 `name()`이나 명시적 필드 값을 사용한다.
- enum 생성자는 항상 `private`이다 (명시하지 않아도 자동으로 `private`). `public`으로 선언하면 컴파일 에러.
- 상수별로 필드가 다르면 반드시 **모든 상수**가 생성자 인자를 지정해야 한다.

## 다음 챕터

[Chapter 0-17: 날짜와 시간](../chapter_00_17_date_time/)에서 `java.time` 패키지로 날짜와 시간을 다루는 방법을 배운다.
