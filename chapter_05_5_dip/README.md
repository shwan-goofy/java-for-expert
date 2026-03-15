# Chapter 5-5: DIP (의존 역전 원칙)

## 학습 목표

- DIP(Dependency Inversion Principle)의 의미를 이해한다
- 구체 클래스에 직접 의존하는 코드의 문제를 인식한다
- 인터페이스(추상)에 의존하도록 설계를 전환한다
- 의존성 주입(Dependency Injection)을 통해 DIP를 구현한다

## 핵심 개념

> **"고수준 모듈은 저수준 모듈에 의존해서는 안 된다. 둘 다 추상화에 의존해야 한다."**

구체 클래스에 직접 의존하면 구현이 바뀔 때 호출하는 쪽도 수정해야 한다.
인터페이스에 의존하면 구현체를 교체해도 호출하는 쪽은 수정 불필요.

### 위반 예시

```java
public class NotificationService {
    private final EmailSender emailSender = new EmailSender();  // 구체 클래스에 직접 의존

    public void notify(String message) {
        emailSender.send(message);  // SMS로 교체하려면 이 코드를 수정해야 함
    }
}
```

### 개선 예시

```java
public class NotificationService {
    private final MessageSender sender;  // 추상(인터페이스)에 의존

    public NotificationService(MessageSender sender) {  // 의존성 주입
        this.sender = sender;
    }

    public void notify(String message) {
        sender.send(message);  // 구현체 교체 시 이 코드 수정 불필요
    }
}
```

## 실습 예제

- `MessageSender.java`: 추상 인터페이스
- `EmailSender.java`, `SmsSender.java`: 구체 구현체
- `NotificationService.java`: 추상에 의존하는 고수준 모듈
- `DipMain.java`: 위반/개선 비교, 런타임 구현체 교체 시연

## 주의사항

- DIP는 의존성 방향을 역전시키는 원칙이다 — 구체적인 것이 추상에 의존한다
- 의존성 주입(DI)은 DIP를 구현하는 기법 중 하나다
- Spring Framework의 `@Autowired`가 이 원칙을 실현하는 대표적 도구다
