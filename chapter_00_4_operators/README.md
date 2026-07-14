# Chapter 0-4: 연산자

## 학습 목표

- 산술, 비교, 논리, 대입 연산자를 이해하고 사용한다
- 증감 연산자(`++`, `--`)의 전위/후위 차이를 이해한다
- 삼항 연산자로 조건식을 간단히 표현한다
- 연산자 우선순위와 정수 나눗셈의 특징을 이해한다

## Python과의 비교

| Python | Java |
|---|---|
| `and`, `or`, `not` | `&&`, `\|\|`, `!` |
| `==` (값 비교, 문자열도 값 비교) | `==` (기본형은 값 비교, 참조형은 **주소** 비교) |
| `x = x + 1` 또는 `x += 1` | `x = x + 1;`, `x += 1;`, `x++;` 모두 가능 |
| `a if cond else b` | `cond ? a : b` (삼항 연산자) |
| `7 / 2` → `3.5` (자동 실수 변환) | `7 / 2` → `3` (정수 나눗셈은 소수점 버림) |
| `**` (거듭제곱 연산자) | 거듭제곱 연산자 없음 → `Math.pow()` 사용 |

## 핵심 개념

### 1. 산술 연산자

```java
int a = 7, b = 2;
System.out.println(a + b); // 9
System.out.println(a - b); // 5
System.out.println(a * b); // 14
System.out.println(a / b); // 3  — 정수끼리 나눗셈은 소수점 버림(몫)
System.out.println(a % b); // 1  — 나머지

double result = (double) a / b; // 3.5 — 실수 나눗셈이 필요하면 형변환
```

### 2. 비교 연산자와 논리 연산자

```java
System.out.println(5 > 3);        // true
System.out.println(5 == 5);       // true
System.out.println(5 != 3);       // true

boolean isAdult = (20 >= 19) && (true); // && : AND
boolean isWeekend = (false) || (true);  // || : OR
boolean isNotAdult = !isAdult;          // ! : NOT
```

`&&`와 `||`는 **단축 평가(short-circuit)**를 한다. 앞의 조건만으로 결과가 확정되면 뒤는 평가하지 않는다.

```java
// arr가 null이면 arr.length는 실행되지 않아 NullPointerException을 피할 수 있다
if (arr != null && arr.length > 0) { ... }
```

### 3. 대입 연산자

```java
int x = 10;
x += 5;  // x = x + 5;
x -= 3;  // x = x - 3;
x *= 2;  // x = x * 2;
x /= 4;  // x = x / 4;
x %= 3;  // x = x % 3;
```

### 4. 증감 연산자 (전위 vs 후위)

```java
int i = 5;
int a = i++; // 후위: 먼저 a에 5를 대입한 다음 i를 6으로 증가
int j = 5;
int b = ++j; // 전위: 먼저 j를 6으로 증가시킨 다음 b에 6을 대입

System.out.println(a); // 5
System.out.println(b); // 6
```

### 5. 삼항 연산자

```java
int score = 85;
String grade = (score >= 90) ? "A" : "B";
```

### 6. 연산자 우선순위

```java
int result = 2 + 3 * 4;   // 14 (곱셈이 덧셈보다 우선)
int result2 = (2 + 3) * 4; // 20 (괄호가 가장 우선)
```

우선순위가 헷갈릴 때는 **괄호를 사용해 명시적으로 표현**하는 것이 안전하고 가독성도 좋다.

## 실습 예제

`OperatorsMain.java`에서는 다음을 학습한다:
1. 산술 연산자와 정수/실수 나눗셈의 차이
2. 비교·논리 연산자와 단축 평가
3. 전위/후위 증감 연산자의 결과 차이
4. 삼항 연산자를 활용한 등급 판정

## 주의사항

- 정수끼리의 나눗셈은 소수점 이하가 버려진다. 실수 결과가 필요하면 피연산자 중 하나를 `double`로 형변환한다.
- 참조형의 `==` 비교는 값이 아니라 **주소**를 비교한다. 문자열 내용 비교는 `.equals()`를 사용한다 (자세한 내용은 심화 챕터에서 다룬다).
- `&&`, `||`는 단축 평가되지만 `&`, `|`는 단축 평가되지 않는다 (양쪽 모두 평가).

## 다음 챕터

[Chapter 0-5: 조건문](../chapter_00_5_conditionals/)에서 연산자의 결과를 활용해 분기 처리하는 방법을 배운다.
