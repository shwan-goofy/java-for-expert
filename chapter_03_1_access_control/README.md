# Chapter 3-1: 접근 제어자

## 학습 목표

- Java의 4단계 접근 제어자를 이해한다
- 컴파일 타임에 강제되는 접근 제어의 의미를 이해한다
- 패키지 구조와 접근 제어의 관계를 파악한다
- Python의 관례 기반 접근 제어와의 철학적 차이를 이해한다

## Python과의 비교

| Python | Java |
|--------|------|
| `name` (public, 관례) | `public` — 어디서든 접근 |
| `_name` (protected, 관례) | `protected` — 같은 패키지 + 서브클래스 |
| 없음 | package-private (default) — 같은 패키지만 |
| `__name` (name mangling) | `private` — 같은 클래스만 |
| 런타임에 우회 가능 | 컴파일 타임에 강제 |

> Python은 "성인들의 관례"로 접근 제어를 처리한다. Java는 컴파일러가 강제한다.

## 핵심 개념

### 1. 4단계 접근 제어

| 접근 제어자 | 같은 클래스 | 같은 패키지 | 서브클래스 | 외부 |
|-------------|:-----------:|:-----------:|:----------:|:----:|
| `public`    | O | O | O | O |
| `protected` | O | O | O | X |
| (default)   | O | O | X | X |
| `private`   | O | X | X | X |

```java
public class BankAccount {
    public String ownerName;      // 누구나 접근
    protected int branchCode;     // 같은 패키지 + 서브클래스
    int accountType;              // 같은 패키지만 (default)
    private int balance;          // 이 클래스만
}
```

### 2. 캡슐화의 원칙

필드는 `private`, 접근이 필요한 경우만 `public` 메서드로 노출한다.
내부 구현을 숨기고 공개 인터페이스만 유지하면 내부 변경이 외부에 영향을 주지 않는다.

```java
public class BankAccount {
    private int balance;  // 직접 접근 불가

    public void deposit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("입금액은 양수여야 합니다");
        balance += amount;
    }

    public int getBalance() { return balance; }
}
```

### 3. 패키지 수준 접근 (package-private)

접근 제어자를 명시하지 않으면 같은 패키지 내에서만 접근 가능하다.
패키지를 모듈 경계로 활용할 때 유용하다.

```java
// 외부에 노출하지 않는 내부 구현 클래스
class InternalProcessor {  // public 없음 — 패키지 내부용
    void process() { ... }
}
```

### 4. Python과의 철학적 차이

- **Python**: `__name`은 이름 맹글링으로 접근을 어렵게 할 뿐, `obj._ClassName__name`으로 우회 가능
- **Java**: `private`은 컴파일러가 강제 — 리플렉션 제외 시 우회 불가

Python은 개발자 신뢰 기반, Java는 컴파일러 강제 기반이다.
Java의 강제 접근 제어는 대규모 팀에서 API 경계를 명확히 하는 데 유리하다.

## 실습 예제

`BankAccount.java`에서는 다음을 학습한다:
- private 잔액 필드와 public 메서드를 통한 캡슐화
- protected 필드 (서브클래스 접근 허용)

`AccessControlMain.java`에서는 다음을 학습한다:
- 각 접근 수준별 접근 가능 여부 확인
- 캡슐화로 유효성 검사 강제

## 주의사항

- `public` 필드는 절대 피한다 — 유효성 검사 없이 값이 변경될 수 있다
- `protected`는 상속 계층에서만 사용 — 무분별하게 사용하면 캡슐화가 깨진다
- 패키지 구조가 접근 제어의 경계 역할을 하므로 패키지 설계가 중요하다

## 다음 챕터

[Chapter 3-2: Getter/Setter와 Lombok, Record](../chapter_03_2_getter_setter/)에서는 접근자 메서드와 Lombok @Data를 배운다.
