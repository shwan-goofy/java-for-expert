# Chapter 0-13: 불변 객체

## 학습 목표

- 불변 객체(Immutable Object)의 개념과 필요성을 이해한다
- 불변 객체를 만드는 방법(필드 `final`, setter 제거, 방어적 복사)을 익힌다
- 가변 객체를 공유할 때 발생할 수 있는 부작용(side effect)을 이해한다
- 값 변경이 필요할 때 새 객체를 반환하는 패턴(with-메서드)을 이해한다

## Python과의 비교

| Python | Java |
|---|---|
| 튜플(tuple), `frozenset` — 불변 컨테이너 | 필드를 `final` + setter 없음으로 불변 클래스 직접 설계 |
| `@dataclass(frozen=True)` | 생성자만 제공하고 setter를 만들지 않는 방식으로 구현 |
| 리스트를 공유하면 원본이 바뀌는 문제 동일하게 존재 | 배열/컬렉션 필드를 그대로 반환하면 원본이 외부에서 변경될 수 있음 |

## 핵심 개념

### 1. 불변 객체란?

**생성된 이후 상태(필드 값)가 절대 변하지 않는 객체**다. 값을 바꾸고 싶으면 기존 객체를 수정하는 대신
**새 객체를 만들어서 반환**한다. 자바 표준 라이브러리의 `String`, `Integer` 같은 래퍼 클래스, `LocalDate` 등이
대표적인 불변 클래스다.

### 2. 가변 객체의 문제 — 의도치 않은 변경

```java
public class MutableMoney {
    private int amount;
    public MutableMoney(int amount) { this.amount = amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public int getAmount() { return amount; }
}

MutableMoney price = new MutableMoney(10000);
MutableMoney reference = price;  // 같은 객체를 참조
reference.setAmount(0);          // reference를 통해 값을 바꿨는데
System.out.println(price.getAmount()); // 0 — price도 함께 바뀜! (의도치 않은 부작용)
```

여러 곳에서 같은 객체를 참조하고 있을 때, 한 곳에서 값을 바꾸면 다른 곳도 영향을 받는다.
이런 부작용은 코드가 복잡해질수록 추적하기 어려운 버그의 원인이 된다.

### 3. 불변 클래스 만드는 방법

```java
public final class Money {                  // 1. 상속으로 불변성이 깨지지 않도록 final 클래스
    private final int amount;                // 2. 필드를 final로 선언

    public Money(int amount) {               // 3. 생성자에서만 값을 초기화
        this.amount = amount;
    }

    public int getAmount() {                 // 4. getter만 제공, setter는 만들지 않음
        return amount;
    }

    // 5. 값을 "바꾸는" 것처럼 보이지만 실제로는 새 객체를 반환
    public Money add(int value) {
        return new Money(this.amount + value);
    }
}

Money price = new Money(10000);
Money discounted = price.add(-1000); // 기존 price는 그대로, 새로운 객체가 생성됨
System.out.println(price.getAmount());      // 10000 — 원본 불변
System.out.println(discounted.getAmount()); // 9000 — 새 객체
```

### 4. 참조형 필드의 방어적 복사(Defensive Copy)

필드가 배열이나 컬렉션(참조형)이면, 생성자에서 그대로 참조를 저장하거나 getter가 원본을 그대로 반환하면
**외부에서 내부 상태를 변경**할 수 있어 불변성이 깨진다.

```java
public final class Team {
    private final List<String> members;

    public Team(List<String> members) {
        this.members = new ArrayList<>(members); // 방어적 복사 — 외부 리스트와 연결을 끊음
    }

    public List<String> getMembers() {
        return List.copyOf(members); // 또는 Collections.unmodifiableList — 외부에서 수정 불가능한 뷰 반환
    }
}
```

방어적 복사가 없으면 다음처럼 불변성이 깨질 수 있다.

```java
List<String> original = new ArrayList<>(List.of("철수", "영희"));
Team team = new Team(original);
original.add("훈이"); // 방어적 복사가 없다면 team.members도 함께 바뀜!
```

## 실습 예제

`MutableMoney.java` / `Money.java`에서는 가변 객체와 불변 객체를 나란히 비교한다.

`Team.java`에서는 다음을 학습한다:
1. 컬렉션 필드에 대한 방어적 복사 (생성자에서 복사, getter에서 불변 뷰 반환)
2. 방어적 복사가 없을 때 원본 리스트 변경이 내부 상태에 영향을 주는 문제

`ImmutableMain.java`에서는 다음을 학습한다:
1. 가변 객체를 공유했을 때 발생하는 의도치 않은 변경
2. 불변 객체의 `add()` 메서드가 새 객체를 반환하는 것을 확인
3. 방어적 복사가 있는 경우와 없는 경우의 차이 비교

## 주의사항

- 불변 클래스는 상속을 통해 가변 서브클래스가 만들어지는 것을 막기 위해 보통 `final` 클래스로 선언한다.
- 필드가 `final`이어도, 그 필드가 **가변 객체(배열, 리스트 등)를 참조**하고 있다면 내부 상태가 바뀔 수 있다. `final`은 "재할당 금지"이지 "내용 변경 금지"가 아니다.
- 값 변경 메서드는 기존 객체를 수정하지 않고 **새 객체를 반환**해야 진짜 불변 객체다.

## 다음 챕터

[Chapter 0-14: String 클래스](../chapter_00_14_string_class/)에서 자바에서 가장 많이 사용되는
대표적인 불변 클래스인 `String`을 자세히 배운다.
