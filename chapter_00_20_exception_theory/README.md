# Chapter 0-20: 예외 처리 1 - 이론

## 학습 목표

- 자바 예외 계층 구조(`Throwable`, `Error`, `Exception`)를 이해한다
- 체크 예외(Checked Exception)와 언체크 예외(Unchecked Exception)의 차이를 이해한다
- `try-catch-finally` 구문의 실행 흐름을 이해한다
- `throw`와 `throws`의 차이를 구분한다

## Python과의 비교

| Python | Java |
|---|---|
| `try / except / finally` | `try / catch / finally` (동일한 구조) |
| 모든 예외는 `raise`로 발생, 처리 강제 없음 | 체크 예외는 반드시 `catch`하거나 `throws`로 선언해야 함 (컴파일러가 강제) |
| `except Exception as e:` | `catch (Exception e) {}` |
| 커스텀 예외: `class MyError(Exception):` | 커스텀 예외: `class MyException extends Exception {}` |
| `raise` | `throw` |

## 핵심 개념

### 1. 예외 계층 구조

```
Throwable
├── Error              — 시스템 레벨 심각한 오류 (메모리 부족 등). 애플리케이션에서 잡지 않는다.
└── Exception
    ├── RuntimeException (언체크 예외)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── IllegalArgumentException
    │   └── ...
    └── 그 외 Exception (체크 예외)
        ├── IOException
        ├── SQLException
        └── ...
```

- `Error`: JVM 자체의 심각한 문제(`OutOfMemoryError` 등). 코드로 복구할 수 없어 잡지 않는다.
- `Exception`: 애플리케이션에서 처리 가능한 예외. 이 아래에 두 종류가 있다.

### 2. 체크 예외 vs 언체크 예외

**체크 예외(Checked Exception)**: `RuntimeException`을 상속하지 않는 예외. 컴파일러가
**반드시 처리(`catch`)하거나 메서드 시그니처에 선언(`throws`)하도록 강제**한다.

```java
public void readFile(String path) throws IOException { // 체크 예외는 throws 선언 필수
    FileReader reader = new FileReader(path); // IOException을 던질 수 있는 메서드
}
```

**언체크 예외(Unchecked Exception)**: `RuntimeException`을 상속한 예외. 컴파일러가 처리를 강제하지 않는다.
주로 **프로그래밍 실수**(잘못된 인자, null 참조 등)를 나타낸다.

```java
public int divide(int a, int b) {
    if (b == 0) {
        throw new IllegalArgumentException("0으로 나눌 수 없습니다"); // throws 선언 없이도 컴파일됨
    }
    return a / b;
}
```

| 구분 | 체크 예외 | 언체크 예외 |
|---|---|---|
| 상속 | `Exception` (RuntimeException 제외) | `RuntimeException` |
| 컴파일러 처리 강제 | O (catch 또는 throws 필수) | X |
| 의미 | 외부 요인으로 발생 가능한, 복구를 고려해볼 상황 (파일 없음, 네트워크 오류 등) | 주로 코드 버그 (잘못된 인자, null 처리 누락 등) |

### 3. try-catch-finally

```java
try {
    int result = 10 / 0; // ArithmeticException 발생
    System.out.println("이 줄은 실행되지 않는다");
} catch (ArithmeticException e) {
    System.out.println("예외 처리: " + e.getMessage());
} finally {
    System.out.println("finally는 예외 발생 여부와 상관없이 항상 실행된다");
}
```

- `catch`는 위에서부터 순서대로 검사하며, **더 구체적인 예외를 먼저, 더 포괄적인 예외(`Exception`)를 나중에** 작성해야 한다.
- `finally` 블록은 예외가 발생하든 안 하든, `return`으로 메서드를 빠져나가든 **항상 실행**된다. 자원 정리(파일 닫기 등)에 사용된다.

```java
try {
    catchMultiple(-1);
} catch (IllegalArgumentException | NullPointerException e) { // 멀티 catch — 여러 예외를 한 번에 처리
    System.out.println("잘못된 값: " + e.getMessage());
}
```

### 4. throw vs throws

- **`throw`**: 실제로 예외 객체를 **발생시키는** 문장. 메서드 본문 안에서 사용.
- **`throws`**: 이 메서드가 어떤 체크 예외를 **던질 수 있다고 선언**하는 것. 메서드 시그니처에 사용.

```java
public void validate(int age) throws InvalidAgeException { // throws — 선언
    if (age < 0) {
        throw new InvalidAgeException("나이는 음수일 수 없습니다"); // throw — 실제 발생
    }
}
```

### 5. 예외 정보 확인하기

```java
try {
    throw new RuntimeException("문제가 발생했습니다");
} catch (RuntimeException e) {
    System.out.println(e.getMessage());       // 문제가 발생했습니다
    System.out.println(e.getClass().getName()); // java.lang.RuntimeException
    e.printStackTrace();                        // 예외 발생 위치를 포함한 전체 스택 추적 출력
}
```

## 실습 예제

`ExceptionTheoryMain.java`에서는 다음을 학습한다:
1. `RuntimeException`(언체크)과 체크 예외의 컴파일러 처리 차이
2. `try-catch-finally`의 실행 순서 확인 (정상 흐름, 예외 발생, `return`이 있는 경우 모두)
3. 멀티 catch(`|`)로 여러 예외 타입을 한 번에 처리
4. `getMessage()`, `getClass()`, `printStackTrace()`로 예외 정보 확인

## 주의사항

- 체크 예외를 던지는 메서드를 호출하려면 반드시 `try-catch`로 처리하거나, 호출하는 메서드도 `throws`로 위임해야 한다. 그렇지 않으면 컴파일 에러가 발생한다.
- `catch (Exception e)`처럼 너무 포괄적인 예외로 한 번에 잡으면, 어떤 문제가 실제로 발생했는지 파악하기 어려워진다. 가능한 한 구체적인 예외 타입으로 잡는다.
- `finally`에서 `return`을 사용하면 `try`/`catch`의 `return` 값을 덮어써 버그의 원인이 될 수 있으므로 피한다.

## 다음 챕터

[Chapter 0-21: 예외 처리 2 - 실습](../chapter_00_21_exception_practice/)에서 커스텀 예외와 실전 예외 처리 패턴을 배운다.
