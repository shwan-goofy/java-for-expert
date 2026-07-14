# Chapter 0-10: 메서드

## 학습 목표

- 메서드를 정의하고 호출하는 방법을 익힌다
- 매개변수(parameter)와 반환값(return value)의 개념을 이해한다
- 같은 이름의 메서드를 여러 개 정의하는 오버로딩을 이해한다
- 메서드로 코드를 재사용 가능한 단위로 나누는 이유를 이해한다

## Python과의 비교

| Python | Java |
|---|---|
| `def add(a, b):` | `int add(int a, int b) { }` (반환 타입 명시 필수) |
| 반환값 없으면 자동으로 `None` | 반환값 없으면 `void` 명시 필수 |
| 하나의 이름에 함수 하나만 존재 (나중 정의가 덮어씀) | 오버로딩으로 같은 이름에 여러 메서드 정의 가능 |
| 기본값 매개변수 지원 (`def f(a, b=10):`) | 기본값 매개변수 없음 → 오버로딩으로 대체 |
| 함수는 `class` 밖에도 존재 가능 | 메서드는 반드시 클래스 안에 존재 |

## 핵심 개념

### 1. 메서드 정의와 호출

```java
public class Calculator {
    // 반환타입 메서드명(매개변수타입 매개변수명) { 본문 }
    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int result = add(3, 5); // 호출
        System.out.println(result); // 8
    }
}
```

- 반환값이 있으면 반드시 `return`으로 값을 돌려줘야 하고, 반환 타입을 메서드 앞에 명시한다.
- 반환값이 없으면 `void`를 사용하고 `return`은 생략하거나 값 없이 `return;`만 쓸 수 있다.

```java
public static void printGreeting(String name) {
    System.out.println("안녕하세요, " + name);
    // return 문 생략 가능
}
```

### 2. 매개변수(Parameter)와 인자(Argument)

```java
public static int multiply(int x, int y) { // x, y는 매개변수(parameter)
    return x * y;
}

int result = multiply(3, 4); // 3, 4는 인자(argument)
```

매개변수는 메서드 안에서만 유효한 **지역 변수**다 (0-7에서 배운 블록 스코프와 동일한 개념).

### 3. 메서드 오버로딩(Overloading)

이름은 같지만 **매개변수의 타입이나 개수가 다른** 메서드를 여러 개 정의할 수 있다.

```java
public static int add(int a, int b) {
    return a + b;
}

public static double add(double a, double b) {
    return a + b;
}

public static int add(int a, int b, int c) {
    return a + b + c;
}
```

호출할 때 전달하는 인자의 타입과 개수에 따라 자바 컴파일러가 알맞은 메서드를 자동으로 선택한다.
**반환 타입만 다른 것은 오버로딩이 아니다** — 컴파일 에러가 발생한다.

### 4. 메서드로 코드 재사용하기

```java
// 메서드 없이 중복 작성
System.out.println((10 + 20) / 2.0);
System.out.println((30 + 40) / 2.0);

// 메서드로 추출 — 중복 제거, 의미 부여
public static double average(int a, int b) {
    return (a + b) / 2.0;
}

System.out.println(average(10, 20));
System.out.println(average(30, 40));
```

같은 로직이 반복된다면 메서드로 추출하여 재사용하는 것이 유지보수에 유리하다.

## 실습 예제

`MethodsMain.java`에서는 다음을 학습한다:
1. 반환값이 있는 메서드와 없는(`void`) 메서드
2. 매개변수를 활용한 계산 메서드 작성
3. 메서드 오버로딩 (타입/개수가 다른 `add` 메서드들)
4. 중복 코드를 메서드로 추출해 재사용하는 예시

## 주의사항

- 반환 타입이 있는 메서드는 모든 실행 경로에서 반드시 값을 반환해야 한다. (하나라도 빠지면 컴파일 에러)
- 반환 타입만 다르고 매개변수가 같은 메서드는 오버로딩이 성립하지 않는다.
- 매개변수는 메서드 호출이 끝나면 사라지는 지역 변수이므로, 메서드 밖에서는 접근할 수 없다.

## 다음 챕터

여기까지가 자바 기초 문법(0장) 과정이다.
[Chapter 1-1: 메서드와 스코프](../chapter_01_1_method_scope/)에서는 파라미터 전달 방식(기본형 vs 참조형),
static/인스턴스/지역 변수의 차이, `final`/`var` 키워드 등 좀 더 심화된 내용을 다룬다.
