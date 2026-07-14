# Chapter 0-21: 예외 처리 2 - 실습

## 학습 목표

- 의미 있는 커스텀 예외 클래스를 설계한다
- 저수준 예외를 더 의미 있는 예외로 감싸서 던지는 "예외 전환(Exception Translation)" 패턴을 익힌다
- 예외의 원인(cause)을 보존하는 방법을 익힌다
- try-with-resources로 자원을 안전하게 해제하는 방법을 익힌다
- 실무에서 자주 쓰이는 예외 처리 원칙을 정리한다

## Python과의 비교

| Python | Java |
|---|---|
| `raise NewError(...) from original_error` | `throw new NewException(msg, originalException)` (원인 체이닝) |
| `with open(...) as f:` (컨텍스트 매니저) | `try (AutoCloseable resource = ...) { }` (try-with-resources) |
| 커스텀 예외에 추가 데이터(속성)를 자유롭게 담음 | 커스텀 예외 클래스에 필드를 추가해 부가 정보 전달 |

## 핵심 개념

### 1. 커스텀 예외 설계

의미가 명확한 커스텀 예외는 코드의 의도를 드러내고, 호출하는 쪽이 더 정밀하게 대응할 수 있게 해준다.
실무에서는 언체크 예외(`RuntimeException` 상속)로 만드는 경우가 많다 — 매번 `throws`를 선언하고
호출부마다 강제로 `catch`하게 만드는 체크 예외의 번거로움을 피하기 위해서다.

```java
public class InsufficientBalanceException extends RuntimeException {
    private final int shortage; // 부가 정보 — 얼마나 부족한지

    public InsufficientBalanceException(String message, int shortage) {
        super(message);
        this.shortage = shortage;
    }

    public int getShortage() {
        return shortage;
    }
}
```

### 2. 예외 전환 (Exception Translation)

저수준 라이브러리의 예외(예: `NumberFormatException`, `SQLException`)를 그대로 밖으로 노출하면
호출하는 쪽이 해당 라이브러리를 몰라도 되는 캡슐화 원칙이 깨진다. 이럴 때 **더 의미 있는 예외로 감싸서
다시 던진다.**

```java
public int parseAge(String input) {
    try {
        return Integer.parseInt(input);
    } catch (NumberFormatException e) {
        // 원인(cause)을 함께 전달 — 두 번째 인자로 원본 예외를 넘긴다
        throw new InvalidInputException("나이는 숫자여야 합니다: " + input, e);
    }
}
```

원인을 담은 예외는 `getCause()`로 원본 예외를 추적할 수 있어 디버깅에 유리하다.

```java
try {
    parseAge("스물다섯");
} catch (InvalidInputException e) {
    System.out.println(e.getMessage());          // 나이는 숫자여야 합니다: 스물다섯
    System.out.println(e.getCause());             // java.lang.NumberFormatException: ...
}
```

### 3. try-with-resources — 자원의 안전한 해제

`AutoCloseable`을 구현한 자원(파일, 커넥션 등)은 `try(...)` 괄호 안에서 선언하면
블록이 끝날 때(예외 발생 여부와 상관없이) **자동으로 `close()`가 호출**된다.

```java
try (AutoCloseable resource = new SimpleResource("파일1")) {
    System.out.println("자원 사용 중");
} catch (Exception e) {
    System.out.println("예외 발생: " + e.getMessage());
}
// close()가 자동으로 호출됨 — finally에서 직접 close()를 호출할 필요가 없다
```

과거에는 `finally` 블록에서 직접 `close()`를 호출해야 했지만, 이 방식은 `close()` 자체가 예외를 던질 경우
코드가 복잡해지는 문제가 있었다. `try-with-resources`는 이 문제를 언어 차원에서 해결한다.

### 4. 실무 예외 처리 원칙 정리

1. **구체적인 예외를 사용한다.** `Exception`, `RuntimeException`을 직접 던지지 않고 의미가 명확한 타입을 만든다.
2. **예외를 삼키지 않는다.** `catch` 블록을 비워두면(`catch (Exception e) {}`) 문제가 조용히 사라져 디버깅이 불가능해진다. 최소한 로그라도 남긴다.
3. **원인을 보존한다.** 예외를 감싸서 다시 던질 때는 항상 원본 예외를 `cause`로 전달한다.
4. **필요한 곳에서만 잡는다.** 처리할 수 없는 예외는 억지로 잡지 말고 호출한 쪽으로 전파한다.

## 실습 예제

`InsufficientBalanceException.java` / `InvalidInputException.java`에서는 부가 정보와 원인을 가진 커스텀 예외를 정의한다.

`BankAccount.java`에서는 다음을 학습한다:
1. 잔액 부족 시 커스텀 언체크 예외를 던지는 방법
2. 문자열 입력을 파싱하다가 발생한 저수준 예외를 의미 있는 예외로 전환하는 방법 (원인 보존 포함)

`SimpleResource.java`에서는 `AutoCloseable`을 구현해 try-with-resources 대상 자원을 만드는 방법을 학습한다.

`ExceptionPracticeMain.java`에서는 위 클래스들을 조합해 다음을 확인한다:
1. 잔액 부족 예외 발생과 부가 정보(`getShortage()`) 확인
2. 예외 전환 후 `getCause()`로 원본 예외 확인
3. try-with-resources로 자원이 자동으로 해제되는 것을 로그로 확인
4. 예외를 잘못 처리하는 예(빈 catch)와 올바르게 처리하는 예 비교

## 주의사항

- `catch` 블록을 비워두지 않는다. 예외를 무시해야 하는 경우가 정말 있다면, 최소한 왜 무시하는지 주석으로 남긴다.
- 예외를 전환할 때 원인(cause)을 빠뜨리면 실제 원인 파악이 어려워진다. 항상 원본 예외를 함께 전달한다.
- try-with-resources 대상은 `AutoCloseable`(또는 `Closeable`)을 구현해야 하며, 여러 자원을 동시에 선언할 수도 있다(`try (A a = ...; B b = ...)`).

## 다음 챕터

여기까지 자바 중급 과정([0-11 ~ 0-21](../chapter_00_11_intro_intermediate/))을 마쳤다.
이 저장소의 [chapter_01](../chapter_01_1_method_scope/) 이후 챕터에서 제네릭, 클래스 설계,
상속, SOLID 원칙, 중첩 클래스 심화 내용을 이어서 학습한다.
