# Chapter 5-2: OCP (개방-폐쇄 원칙)

## 학습 목표

- OCP(Open-Closed Principle)의 의미를 이해한다
- 새 기능 추가 시 기존 코드를 수정하지 않고 확장만 하는 설계를 익힌다
- 전략 패턴(Strategy Pattern)으로 OCP를 구현한다

## 핵심 개념

> **"소프트웨어 개체는 확장에는 열려 있고, 수정에는 닫혀 있어야 한다."**

새 할인 정책이 생겼을 때 기존 `OrderService` 코드를 수정해야 한다면 OCP 위반이다.
`DiscountPolicy` 인터페이스를 주입받으면 새 정책 클래스를 추가하는 것만으로 확장된다.

### 위반 예시

```java
// 새 할인 정책이 생길 때마다 이 메서드를 수정해야 한다
public int calculatePrice(int price, String type) {
    if (type.equals("RATE")) return (int)(price * 0.9);
    if (type.equals("FIXED")) return price - 1000;
    // 새 타입 추가 시 여기를 수정...
    return price;
}
```

### 개선 예시

```java
public interface DiscountPolicy {
    int apply(int price);
}

// 새 정책은 새 클래스로 추가 — 기존 코드 무수정
public class RateDiscountPolicy implements DiscountPolicy { ... }
public class FixedDiscountPolicy implements DiscountPolicy { ... }
```

## 실습 예제

- `DiscountPolicy.java`: 할인 정책 인터페이스
- `RateDiscountPolicy.java`: 비율 할인 구현체
- `FixedDiscountPolicy.java`: 정액 할인 구현체
- `OcpMain.java`: 위반/개선 비교

## 다음 챕터

[Chapter 5-3: LSP (리스코프 치환 원칙)](../chapter_05_3_lsp/)
