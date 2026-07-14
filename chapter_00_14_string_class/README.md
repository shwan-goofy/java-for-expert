# Chapter 0-14: String 클래스

## 학습 목표

- `String`이 불변 객체([0-13](../chapter_00_13_immutable_object/) 참고)로 설계된 이유를 이해한다
- 문자열 리터럴과 문자열 풀(String Pool)의 동작 방식을 이해한다
- `==`와 `.equals()`의 차이를 문자열에서 명확히 구분한다
- 문자열을 반복적으로 연결할 때 `StringBuilder`를 사용해야 하는 이유를 이해한다
- 자주 쓰는 `String` 메서드(`substring`, `split`, `trim`, `replace` 등)를 익힌다

## Python과의 비교

| Python | Java |
|---|---|
| 문자열은 불변(immutable) | 문자열은 불변(immutable) — 동일한 설계 철학 |
| `s1 is s2` (인터닝은 구현에 따라 다름) | 리터럴은 String Pool에서 재사용, `new String()`은 새 객체 |
| `s1 == s2` (내용 비교) | `s1 == s2` (주소 비교! 내용 비교는 `.equals()`) |
| `"".join(list)` 또는 `+=` 반복 (CPython 일부 최적화) | 반복 연결 시 `StringBuilder` 명시적으로 사용 권장 |
| `s[1:3]`, `s.split()`, `s.strip()` | `s.substring(1,3)`, `s.split()`, `s.trim()`/`s.strip()` |

## 핵심 개념

### 1. String은 불변 객체다

`String`의 모든 메서드(`substring`, `replace`, `toUpperCase` 등)는 **원본을 바꾸지 않고 새 문자열을 반환**한다.

```java
String original = "hello";
String upper = original.toUpperCase();
System.out.println(original); // hello — 원본은 그대로
System.out.println(upper);    // HELLO — 새 문자열
```

### 2. 문자열 리터럴과 String Pool

문자열 리터럴(`"hello"`)은 **String Pool**이라는 특별한 메모리 영역에 저장되고, 같은 내용의 리터럴은 재사용된다.
반면 `new String(...)`은 Pool과 무관하게 항상 새로운 객체를 힙(heap)에 생성한다.

```java
String a = "hello";
String b = "hello";
System.out.println(a == b); // true — 같은 String Pool의 객체를 재사용

String c = new String("hello");
System.out.println(a == c); // false — c는 힙에 새로 생성된 별개의 객체
System.out.println(a.equals(c)); // true — 내용은 같음
```

### 3. == 대신 항상 equals()를 사용한다

문자열은 참조형이므로 `==`는 **주소**를 비교한다. 문자열 내용을 비교할 때는 반드시 `.equals()`를 사용해야 한다.

```java
String input = new String("apple");
if (input == "apple") { ... }        // 위험 — 대부분 false가 되어 버그가 됨
if (input.equals("apple")) { ... }   // 올바른 방법
```

### 4. 문자열 연결과 StringBuilder

`+` 연산자로 문자열을 연결하면 매번 **새로운 String 객체가 생성**된다.
반복문 안에서 `+`로 문자열을 계속 이어붙이면 매 반복마다 새 객체가 만들어져 성능이 크게 나빠진다.

```java
// 나쁜 예 — 반복마다 새로운 String 객체 생성 (반복 횟수가 많으면 느려짐)
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i;
}

// 좋은 예 — 내부 버퍼에 이어붙이고 마지막에 한 번만 String으로 변환
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
String result2 = sb.toString();
```

`StringBuilder`는 가변(mutable) 객체이며, 메서드 체이닝도 지원한다.

```java
String message = new StringBuilder()
        .append("이름: ").append("홍길동")
        .append(", 나이: ").append(20)
        .toString();
```

### 5. 자주 쓰는 String 메서드

```java
String s = "  Hello, Java World!  ";

s.length();                 // 22 (공백 포함 길이)
s.trim();                   // "Hello, Java World!" (양 끝 공백 제거)
s.toUpperCase();             // "  HELLO, JAVA WORLD!  "
s.toLowerCase();             // "  hello, java world!  "
s.trim().substring(0, 5);    // "Hello" (인덱스 0부터 5 직전까지)
s.trim().replace("Java", "Kotlin"); // "Hello, Kotlin World!"
s.trim().split(", ");        // ["Hello", "Java World!"]
s.contains("Java");          // true
s.trim().equalsIgnoreCase("HELLO, JAVA WORLD!"); // true — 대소문자 무시 비교
```

## 실습 예제

`StringClassMain.java`에서는 다음을 학습한다:
1. `String`의 불변성 확인 (`toUpperCase` 등이 원본을 바꾸지 않음)
2. 리터럴과 `new String()`의 `==` 비교 차이, String Pool 동작
3. 반복 연결 시 `+`와 `StringBuilder`의 결과 비교
4. `substring`, `split`, `trim`, `replace` 등 실전에서 자주 쓰는 메서드

## 주의사항

- 문자열 비교는 항상 `.equals()`를 사용한다. `==`는 우연히 같은 리터럴일 때만 `true`가 나와 버그를 숨길 수 있다.
- 반복문 안에서의 문자열 연결은 `StringBuilder`를 사용한다. 특히 반복 횟수가 많을수록 성능 차이가 커진다.
- `substring(start, end)`는 `end` 인덱스를 **포함하지 않는다** (`[start, end)` 구간).

## 다음 챕터

[Chapter 0-15: 래퍼, Class 클래스](../chapter_00_15_wrapper_class/)에서 기본형을 객체로 다루는 래퍼 클래스를 배운다.
