# Chapter 5-1: SRP (단일 책임 원칙)

## 학습 목표

- SRP(Single Responsibility Principle)의 의미를 이해한다
- 하나의 클래스가 여러 책임을 가질 때의 문제를 인식한다
- 책임을 분리해 변경의 이유가 하나가 되도록 설계한다

## 핵심 개념

> **"하나의 클래스는 하나의 변경 이유만 가져야 한다."** — Robert C. Martin

SRP는 클래스가 하나의 책임(역할)만 가져야 한다는 원칙이다.
책임이 많아질수록 변경 이유가 늘어나고, 변경 시 다른 책임에 영향을 준다.

### 위반 예시

```java
// 나쁜 설계 — Order가 주문 데이터, 저장, 이메일 발송까지 모두 담당
public class Order {
    private List<String> items;

    public void addItem(String item) { items.add(item); }  // 주문 데이터 관리

    public void saveToDatabase() { /* DB 저장 로직 */ }  // 저장 책임

    public void sendConfirmEmail() { /* 이메일 발송 */ }  // 알림 책임
}
```

DB 저장 방식이 바뀌면 `Order`를 수정해야 한다.
이메일 서비스가 바뀌면 `Order`를 수정해야 한다. → 변경 이유가 여러 개

### 개선 예시

```java
public class Order { /* 주문 데이터만 관리 */ }
public class OrderRepository { /* DB 저장만 담당 */ }
public class EmailNotifier { /* 이메일 발송만 담당 */ }
```

## 실습 예제

- `Order.java`: 주문 데이터와 비즈니스 로직만 담당
- `OrderRepository.java`: 주문 저장/조회 담당
- `EmailNotifier.java`: 이메일 알림 담당
- `SrpMain.java`: 위반 예시와 개선 예시 실행

## 주의사항

- 책임의 경계는 "변경 이유"를 기준으로 판단한다
- 너무 작게 분리하면 오히려 복잡도가 증가한다 (과도한 분리 금지)

## 다음 챕터

[Chapter 5-2: OCP (개방-폐쇄 원칙)](../chapter_05_2_ocp/)
