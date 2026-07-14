# Chapter 0-5: 조건문

## 학습 목표

- `if`, `else if`, `else`로 조건에 따라 코드를 분기한다
- 전통적인 `switch`문과 자바 14+의 화살표(`->`) `switch`식을 사용한다
- 중첩 조건문을 읽기 쉽게 작성하는 방법을 익힌다

## Python과의 비교

| Python | Java |
|---|---|
| `if cond:` (콜론 + 들여쓰기) | `if (cond) { }` (괄호 + 중괄호) |
| `elif` | `else if` |
| `match ... case` (3.10+) | `switch (value) { case ... }` |
| 조건식에 임의의 truthy 값 사용 가능 | 조건식은 반드시 `boolean` 타입이어야 함 |

## 핵심 개념

### 1. if - else if - else

```java
int score = 85;

if (score >= 90) {
    System.out.println("A");
} else if (score >= 80) {
    System.out.println("B");
} else if (score >= 70) {
    System.out.println("C");
} else {
    System.out.println("F");
}
```

- 조건식은 반드시 `boolean` 타입이어야 한다. (Python처럼 `if (1)` 같은 표현은 자바에서 컴파일 에러)
- 중괄호 `{}`는 생략할 수 있지만(문장이 1개일 때), **항상 사용하는 것을 권장**한다. (버그 방지)

### 2. switch 문 (전통 방식)

```java
int day = 3;
String dayName;

switch (day) {
    case 1:
        dayName = "월요일";
        break;
    case 2:
        dayName = "화요일";
        break;
    case 3:
        dayName = "수요일";
        break;
    default:
        dayName = "알 수 없음";
        break;
}
```

`break`를 빠뜨리면 다음 `case`까지 계속 실행되는 **폴스루(fall-through)** 현상이 발생한다. 의도한 것이 아니라면 주의해야 한다.

### 3. switch 식 (Java 14+, 화살표 문법)

```java
String dayName = switch (day) {
    case 1 -> "월요일";
    case 2 -> "화요일";
    case 3 -> "수요일";
    default -> "알 수 없음";
};
```

- `->` 문법은 `break`가 필요 없고 폴스루가 발생하지 않는다.
- `switch`를 **값을 만들어 반환하는 식(expression)**으로 바로 사용할 수 있다.

### 4. 중첩 조건문

```java
int age = 25;
boolean hasLicense = true;

if (age >= 18) {
    if (hasLicense) {
        System.out.println("운전 가능");
    } else {
        System.out.println("면허가 필요합니다");
    }
} else {
    System.out.println("나이 제한에 걸립니다");
}
```

중첩이 깊어지면 가독성이 떨어지므로, 논리 연산자(`&&`)로 조건을 합치거나 조기 반환(early return)을 고려한다.

## 실습 예제

`ConditionalsMain.java`에서는 다음을 학습한다:
1. 점수에 따른 등급 판정 (if - else if - else)
2. 요일 출력 (전통 switch, break 활용)
3. 계절 판정 (switch 식, `->` 문법)
4. 나이와 면허 유무에 따른 중첩 조건 판정

## 주의사항

- 자바의 조건식은 반드시 `boolean`이어야 한다. 정수(`0`, `1`)를 조건으로 바로 사용할 수 없다.
- 전통 `switch`문에서 `break`를 빠뜨리지 않도록 주의한다. (또는 `->` 문법을 사용해 아예 그 문제를 없앤다)
- `if`의 중괄호를 생략하면 나중에 코드를 추가할 때 실수하기 쉬우므로 항상 중괄호를 사용한다.

## 다음 챕터

[Chapter 0-6: 반복문](../chapter_00_6_loops/)에서 특정 코드를 반복 실행하는 방법을 배운다.
