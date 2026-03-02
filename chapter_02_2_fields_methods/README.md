# Chapter 2-2: 필드와 메서드

## 학습 목표

- 인스턴스 변수와 static 변수의 차이를 이해한다
- 인스턴스 메서드, static 메서드, 정적 팩토리 메서드를 구분하고 사용한다
- static 초기화 블록과 인스턴스 초기화 블록의 실행 순서를 이해한다

## Python과의 비교

| Python | Java |
|--------|------|
| 인스턴스 변수 (`self.x`) | 인스턴스 변수 (`this.x`) |
| 클래스 변수 (`ClassName.x`) | static 변수 (`static int x`) |
| `@classmethod` | `static` 메서드 (완전히 동일하지 않음) |
| `@staticmethod` | `static` 메서드 |
| `ClassName.of(...)` 관례 없음 | 정적 팩토리 메서드 (`of`, `from`, `create`) 관례 |

## 핵심 개념

### 1. 인스턴스 변수 vs static 변수

| 구분 | 인스턴스 변수 | static 변수 |
|------|--------------|-------------|
| 저장 위치 | 힙 (인스턴스마다) | 메서드 영역 (하나) |
| 생명주기 | 인스턴스와 동일 | 클래스 로딩 ~ JVM 종료 |
| 접근 방법 | `인스턴스.필드` | `클래스명.필드` |
| 용도 | 각 객체의 상태 | 모든 인스턴스가 공유하는 상태 |

```java
public class Counter {
    static int totalCount = 0;  // static 변수 — 모든 Counter가 공유
    int count;                  // 인스턴스 변수 — Counter마다 독립

    public Counter() {
        totalCount++;  // 생성될 때마다 증가
    }
}
```

### 2. 인스턴스 메서드 vs static 메서드

- **인스턴스 메서드**: 반드시 인스턴스를 통해 호출. 인스턴스 변수 접근 가능
- **static 메서드**: 클래스명으로 직접 호출. 인스턴스 변수 접근 불가 (인스턴스가 없으므로)

```java
public class MathUtils {
    // static 메서드 — 상태 불필요, 순수 함수
    public static int square(int n) {
        return n * n;
    }
}

MathUtils.square(5);  // 인스턴스 없이 호출
```

### 3. 정적 팩토리 메서드 (Static Factory Method)

`new` 대신 `static` 메서드로 인스턴스를 생성한다.

**장점**:
- 이름으로 생성 의도를 표현할 수 있다 (`of`, `from`, `withName`)
- 같은 파라미터 타입으로 여러 생성 방법을 제공할 수 있다
- 캐싱, 서브타입 반환 등 유연한 구현이 가능하다

```java
public class Member {
    private Member(String name, String role) { ... }

    public static Member ofUser(String name) {
        return new Member(name, "USER");
    }

    public static Member ofAdmin(String name) {
        return new Member(name, "ADMIN");
    }
}

// 생성 의도가 명확
Member user  = Member.ofUser("홍길동");
Member admin = Member.ofAdmin("이몽룡");
```

### 4. 초기화 블록 실행 순서

```
클래스 로딩 시:
  1. static 변수 기본값 할당
  2. static 초기화 블록 실행 (위→아래)
  3. static 변수 명시적 초기화

인스턴스 생성 시 (new 호출):
  1. 인스턴스 변수 기본값 할당
  2. 인스턴스 초기화 블록 실행
  3. 생성자 실행
```

```java
public class InitOrder {
    static int x;
    int y;

    static { x = 10; System.out.println("static 초기화"); }
    { y = 20; System.out.println("인스턴스 초기화"); }

    InitOrder() { System.out.println("생성자"); }
}
```

## 실습 예제

`Counter.java`에서는 다음을 학습한다:
- static 변수로 인스턴스 생성 횟수 추적
- 인스턴스 변수와 static 변수의 독립성

`MemberFactory.java`에서는 다음을 학습한다:
- 정적 팩토리 메서드로 다양한 생성 방법 제공
- 생성 의도를 이름으로 표현

`FieldsMethodsMain.java`에서는 다음을 학습한다:
- 초기화 순서 출력으로 실행 순서 확인
- static / 인스턴스 메서드 구분 실습

## 주의사항

- static 메서드 내에서 `this` 사용 불가 (인스턴스 없음)
- static 변수는 전역 상태를 만드므로 남용 금지
- 정적 팩토리 메서드 이름 관례: `of`, `from`, `valueOf`, `getInstance`, `create`, `with`

## 다음 챕터

[Chapter 2-3: 특수 메서드](../chapter_02_3_special_methods/)에서는 toString(), equals(), hashCode()를 배운다.
