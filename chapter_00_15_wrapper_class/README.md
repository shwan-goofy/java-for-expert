# Chapter 0-15: 래퍼, Class 클래스

## 학습 목표

- 8가지 기본형(primitive)에 대응하는 래퍼(Wrapper) 클래스를 이해한다
- 오토박싱(autoboxing)과 언박싱(unboxing)이 자동으로 일어나는 과정을 이해한다
- 래퍼 클래스의 `==` 비교 시 발생하는 함정(캐싱)을 이해한다
- `Class` 클래스로 런타임 타입 정보를 다루는 기초를 익힌다

## Python과의 비교

| Python | Java |
|---|---|
| 모든 값이 이미 객체 (`int`도 객체) | 기본형(`int` 등)과 객체(`Integer` 등)가 분리되어 있음 |
| 별도의 박싱 개념 없음 | 기본형 ↔ 래퍼 객체 자동 변환: 오토박싱/언박싱 |
| 작은 정수 캐싱은 구현 세부사항(-5~256, CPython) | `Integer`는 -128~127 범위를 캐싱 (`Integer.valueOf`) |
| `type(x)` | `x.getClass()` — 이번 챕터의 `Class` 클래스가 반환 타입 |

## 핵심 개념

### 1. 래퍼 클래스란?

자바의 기본형(`int`, `double`, `boolean` 등)은 객체가 아니다. 하지만 제네릭(`List<Integer>`),
컬렉션 등 **객체만 다룰 수 있는 곳**에 기본형 값을 사용하려면 객체로 감싸야 한다. 이때 사용하는 것이 래퍼 클래스다.

| 기본형 | 래퍼 클래스 |
|---|---|
| `byte` | `Byte` |
| `short` | `Short` |
| `int` | `Integer` |
| `long` | `Long` |
| `float` | `Float` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

```java
List<Integer> numbers = new ArrayList<>(); // List<int> 는 불가능, 래퍼 클래스만 가능
numbers.add(10); // 내부적으로 Integer.valueOf(10) 호출
```

### 2. 오토박싱(Autoboxing) / 언박싱(Unboxing)

자바 5부터는 기본형과 래퍼 객체 간 변환을 컴파일러가 **자동으로** 처리해준다.

```java
int primitive = 10;
Integer boxed = primitive;     // 오토박싱: int -> Integer (내부적으로 Integer.valueOf(10))
int unboxed = boxed;           // 언박싱: Integer -> int (내부적으로 boxed.intValue())

List<Integer> list = new ArrayList<>();
list.add(5);           // 오토박싱
int value = list.get(0); // 언박싱
```

### 3. 래퍼 클래스의 == 비교 함정

래퍼 클래스는 참조형이므로 `==`는 원칙적으로 **주소 비교**다. 그런데 `Integer`는 자주 쓰이는
**-128 ~ 127** 범위의 값을 미리 캐싱해두고 재사용하기 때문에 혼란스러운 결과가 나올 수 있다.

```java
Integer a = 100;
Integer b = 100;
System.out.println(a == b); // true — -128~127 범위는 캐싱되어 같은 객체 재사용

Integer c = 200;
Integer d = 200;
System.out.println(c == d); // false — 범위를 벗어나 각각 새 객체 생성

System.out.println(c.equals(d)); // true — 값 비교는 항상 equals() 사용
```

**래퍼 클래스는 값을 비교할 때 반드시 `.equals()`를 사용해야 한다.** `==`는 우연히 캐시 범위 안에서만 맞는 것처럼
보여 버그를 숨긴다.

### 4. NullPointerException 위험

언박싱 과정에서 래퍼 객체가 `null`이면 `NullPointerException`이 발생한다.

```java
Integer count = null;
int result = count + 1; // NullPointerException — null을 언박싱하려고 시도
```

### 5. Class 클래스 — 런타임 타입 정보

자바의 모든 클래스는 로딩될 때 그 클래스 자체의 정보를 담은 `Class` 객체가 생성된다.
`Class` 객체를 얻는 방법은 여러 가지가 있다.

```java
Class<?> c1 = "hello".getClass();       // 인스턴스에서 얻기
Class<?> c2 = String.class;             // 클래스 리터럴에서 얻기
Class<?> c3 = Class.forName("java.lang.String"); // 클래스 이름(문자열)으로 얻기

System.out.println(c1 == c2); // true — 같은 클래스는 JVM에 단 하나의 Class 객체만 존재

System.out.println(c1.getName());       // java.lang.String
System.out.println(c1.getSimpleName()); // String
System.out.println(c1.isInterface());   // false
```

`Class`는 리플렉션(Reflection, 런타임에 클래스 구조를 분석하고 조작하는 기능)의 시작점이다.
리플렉션 자체는 심화 주제이므로, 여기서는 "타입 정보를 담는 객체가 존재한다"는 개념만 익힌다.

## 실습 예제

`WrapperClassMain.java`에서는 다음을 학습한다:
1. 오토박싱/언박싱이 자동으로 일어나는 상황
2. `Integer` 캐싱 범위(-128~127)에 따른 `==` 비교 결과 차이
3. `null` 언박싱 시 `NullPointerException` 발생 확인
4. `getClass()`, `클래스명.class`, `Class.forName()`으로 `Class` 객체 얻기

## 주의사항

- 래퍼 클래스 값 비교는 항상 `.equals()`를 사용한다. `==`는 캐싱 범위(-128~127) 안에서만 우연히 true가 나온다.
- 래퍼 타입 변수가 `null`일 수 있는 상황에서 산술 연산(언박싱)을 하면 `NullPointerException`이 발생할 수 있다.
- `Class.forName()`은 클래스를 찾지 못하면 체크 예외(`ClassNotFoundException`)를 던진다 ([0-20 예외 처리](../chapter_00_20_exception_theory/) 참고).

## 다음 챕터

[Chapter 0-16: 열거형 - ENUM](../chapter_00_16_enum/)에서 상수 집합을 안전하게 표현하는 enum을 배운다.
