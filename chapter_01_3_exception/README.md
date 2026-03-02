# Chapter 1-3: 예외 처리

## 학습 목표

- Java 예외 계층 구조를 이해한다
- Checked Exception과 Unchecked Exception의 차이를 명확히 구분한다
- try-catch-finally, try-with-resources를 올바르게 사용한다
- 커스텀 예외를 도메인에 맞게 설계하고 작성한다
- 예외 전파 전략(`throws`)을 이해한다

## Python과의 비교

| Python | Java |
|--------|------|
| `Exception` (모두 Unchecked) | Checked / Unchecked 구분 |
| `try/except/finally` | `try/catch/finally` |
| `with` 문 | `try-with-resources` |
| `raise Exception("메시지")` | `throw new Exception("메시지")` |
| 모든 예외 처리 선택적 | Checked는 처리 또는 전파 강제 |

## 핵심 개념

### 1. 예외 계층 구조

```
Throwable
├── Error                  ← JVM 심각한 오류 (OOM 등) — 처리하지 않는다
└── Exception
    ├── IOException        ← Checked Exception
    ├── SQLException       ← Checked Exception
    └── RuntimeException   ← Unchecked Exception
        ├── NullPointerException
        ├── IllegalArgumentException
        ├── IndexOutOfBoundsException
        └── ClassCastException
```

### 2. Checked vs Unchecked Exception

#### Checked Exception

컴파일러가 처리를 **강제**한다. `try-catch`로 잡거나, `throws`로 전파해야 컴파일된다.
외부 자원(파일, DB, 네트워크) 사용 시 발생하는 예외다.

```java
// throws로 전파: 이 메서드를 호출하는 쪽에서 처리 책임
public void readFile(String path) throws IOException {
    // ...
}

// try-catch로 직접 처리
try {
    readFile("data.txt");
} catch (IOException e) {
    System.err.println("파일 읽기 실패: " + e.getMessage());
}
```

#### Unchecked Exception (RuntimeException)

컴파일러가 처리를 **강제하지 않는다**. 주로 프로그래밍 오류를 나타낸다.
도메인 규칙 위반, 잘못된 파라미터 등에 사용한다.

```java
public void withdraw(int amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("출금액은 0보다 커야 합니다: " + amount);
    }
    // ...
}
```

### 3. try-catch-finally

```java
try {
    // 예외가 발생할 수 있는 코드
} catch (IllegalArgumentException e) {
    // IllegalArgumentException 처리
} catch (RuntimeException e) {
    // 나머지 RuntimeException 처리 (더 넓은 타입은 뒤에 위치)
} finally {
    // 예외 여부와 관계없이 반드시 실행 (자원 해제 등)
}
```

### 4. try-with-resources (AutoCloseable)

`AutoCloseable`을 구현한 자원은 블록 종료 시 자동으로 `close()`를 호출한다.
`finally`에서 수동으로 닫을 필요 없다.

```java
try (var connection = openConnection(); var reader = new BufferedReader(...)) {
    // 사용
}  // 블록 종료 시 connection.close(), reader.close() 자동 호출
```

### 5. 커스텀 예외 설계 관례

- 도메인 의미를 담은 이름 사용: `InsufficientBalanceException`
- Unchecked 선호: `RuntimeException` 상속 (Spring 등 주류 프레임워크 방식)
- 메시지에 발생 원인 값 포함
- 원인 예외를 `cause`로 전달 가능 (예외 체이닝)

```java
public class InsufficientBalanceException extends RuntimeException {
    private final int requestedAmount;
    private final int currentBalance;

    public InsufficientBalanceException(int requestedAmount, int currentBalance) {
        super("잔액 부족: 요청=" + requestedAmount + ", 잔액=" + currentBalance);
        this.requestedAmount = requestedAmount;
        this.currentBalance = currentBalance;
    }
}
```

### 6. throws와 예외 전파 전략

- 하위 레이어(Repository, 외부 API)에서 발생한 Checked Exception을 Unchecked로 래핑해서 전파하는 것이 일반적
- 상위 레이어가 직접 처리하도록 계층 경계에서 변환

```java
// 하위 레이어: IOException → 도메인 예외로 변환
try {
    readFile(path);
} catch (IOException e) {
    throw new FileReadException("파일 읽기 실패: " + path, e);  // cause 전달
}
```

### 7. Switch expression에서 예외 (JDK 14+)

```java
String result = switch (status) {
    case "ACTIVE" -> "활성";
    case "INACTIVE" -> "비활성";
    default -> throw new IllegalArgumentException("알 수 없는 상태: " + status);
};
```

## 실습 예제

`CustomException.java`에서는 다음을 학습한다:
- `InsufficientBalanceException` 커스텀 예외 클래스
- 예외 체이닝 (`cause` 전달)

`CheckedExample.java`에서는 다음을 학습한다:
- AutoCloseable 구현으로 try-with-resources 사용
- Checked Exception을 Unchecked로 변환

`ExceptionMain.java`에서는 다음을 학습한다:
- 계층적 catch, multi-catch (`|` 연산자)
- switch expression에서 throw
- 전체 예외 처리 시나리오 실행

## 주의사항

- `catch (Exception e) {}` 빈 catch 블록 사용 금지 — 예외를 삼키면 디버깅 불가
- `finally`에서 return 사용 금지 — try/catch의 return을 무시하게 됨
- 예외 메시지에는 발생 원인 값을 반드시 포함한다
- Checked Exception을 무분별하게 `throws`로 전파하지 말고 적절한 레이어에서 처리한다

## 다음 챕터

[Chapter 2-1: 클래스 정의와 인스턴스화](../chapter_02_1_class_instance/)에서는 클래스와 생성자를 배운다.
