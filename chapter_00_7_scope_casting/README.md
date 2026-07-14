# Chapter 0-7: 스코프, 형변환

## 학습 목표

- 중괄호(`{}`) 블록 단위로 결정되는 변수의 유효 범위(스코프)를 이해한다
- 암시적 형변환(자동 형변환)과 명시적 형변환(강제 형변환)을 구분한다
- 형변환 과정에서 발생할 수 있는 데이터 손실을 이해한다
- 서로 다른 타입 간 연산 시 자동으로 승격(promotion)되는 규칙을 안다

## Python과의 비교

| Python | Java |
|---|---|
| 함수 단위로 스코프가 나뉨 (블록 스코프 없음) | `{}` 블록마다 스코프가 나뉨 (if, for 안 변수는 밖에서 접근 불가) |
| `int(3.9)` → `3` (형변환 함수 호출) | `(int) 3.9` → `3` (캐스팅 연산자) |
| 타입이 동적으로 결정되어 형변환 필요성이 적음 | 타입이 고정되어 있어 형변환이 자주 필요함 |
| 정수는 크기 제한 없이 자동 확장 | 작은 타입 → 큰 타입은 자동, 큰 타입 → 작은 타입은 데이터 손실 위험 |

## 핵심 개념

### 1. 블록 스코프 (Block Scope)

자바에서 변수는 선언된 **중괄호 `{}` 블록 안에서만** 유효하다.

```java
public static void main(String[] args) {
    int x = 10;

    if (x > 5) {
        int y = 20;          // y는 이 if 블록 안에서만 유효
        System.out.println(x + y);
    }

    // System.out.println(y); // 컴파일 에러 — y는 이미 스코프를 벗어남

    for (int i = 0; i < 3; i++) {
        int z = i * 2;        // z는 for문 블록 안에서만 유효, 매 반복마다 새로 생성
    }
    // System.out.println(i); // 컴파일 에러 — i도 for문 스코프에 속함
}
```

바깥 스코프의 변수는 안쪽 블록에서 사용할 수 있지만, 그 반대는 불가능하다.

```java
int outer = 100;
{
    System.out.println(outer); // OK — 바깥 변수는 안에서 접근 가능
    int inner = 1;
}
// System.out.println(inner); // 컴파일 에러
```

같은 이름의 변수를 안쪽 블록에서 **다시 선언할 수는 없다** (Python과 다른 부분).

```java
int a = 1;
if (true) {
    // int a = 2; // 컴파일 에러 — 이미 바깥 스코프에 a가 있음 (섀도잉 금지)
}
```

### 2. 암시적 형변환 (Implicit Casting, 자동 형변환)

**작은 범위 타입 → 큰 범위 타입**으로 변환할 때는 자동으로 이루어진다. 데이터 손실이 없다.

```
byte -> short -> int -> long -> float -> double
```

```java
int i = 100;
long l = i;      // int -> long, 자동 형변환
double d = l;    // long -> double, 자동 형변환
```

### 3. 명시적 형변환 (Explicit Casting, 강제 형변환)

**큰 범위 타입 → 작은 범위 타입**으로 변환할 때는 `(타입)`을 명시해야 한다. 데이터가 손실될 수 있다.

```java
double d = 3.99;
int i = (int) d;   // 3 — 소수점 이하는 버려짐 (반올림 아님)

int big = 300;
byte b = (byte) big; // byte 범위(-128~127)를 넘어서 값이 예상치 못하게 순환됨
System.out.println(b); // 44
```

### 4. 연산 중 자동 승격 (Promotion)

`byte`, `short`, `char`는 연산 시 자동으로 `int`로 승격된다.

```java
byte b1 = 10, b2 = 20;
// byte b3 = b1 + b2; // 컴파일 에러 — 결과가 int로 승격되기 때문
int b3 = b1 + b2;      // OK
```

서로 다른 타입끼리 연산하면 **더 큰 타입 쪽으로 자동 변환**된 뒤 계산된다.

```java
int i = 10;
double d = 3.0;
double result = i + d; // int(10)가 double로 변환되어 계산 -> 13.0
```

## 실습 예제

`ScopeCastingMain.java`에서는 다음을 학습한다:
1. `if`, `for` 블록 스코프와 스코프를 벗어난 접근이 왜 에러인지
2. 암시적 형변환 (`int` → `long` → `double`)
3. 명시적 형변환과 데이터 손실 (`double` → `int`, `int` → `byte` 오버플로)
4. 서로 다른 타입 연산에서의 자동 승격

## 주의사항

- `(int) 3.99`는 반올림이 아니라 **소수점 버림(truncate)**이다. 반올림이 필요하면 `Math.round()`를 사용한다.
- 큰 타입을 작은 타입으로 강제 형변환할 때 값이 범위를 넘으면 **예외 없이 조용히** 잘못된 값이 나온다. 항상 범위를 먼저 확인한다.
- `for`문의 초기화 변수(`int i`)는 `for`문이 끝나면 사라진다. 반복 후에도 값을 써야 한다면 `for`문 밖에 변수를 선언한다.

## 다음 챕터

[Chapter 0-8: 훈련](../chapter_00_8_training/)에서 지금까지 배운 문법을 종합적으로 연습한다.
