# Chapter 1-1: 메서드와 스코프

## 학습 목표

- 메서드의 정의와 호출 방식을 이해한다
- Java의 파라미터 전달 방식 (기본형 vs 참조형)을 이해한다
- 지역 변수, 인스턴스 변수, static 변수의 스코프 차이를 명확히 구분한다
- `final` 키워드와 `var` 키워드의 용도를 이해한다
- 메서드 오버로딩을 이해한다

## Python과의 비교

| Python | Java |
|--------|------|
| `def 함수명(매개변수):` | `반환타입 메서드명(매개변수타입 매개변수)` |
| Call by object reference | 기본형은 값 복사, 참조형은 주소 복사 |
| 전역 변수 (global 키워드) | static 변수 |
| 지역 변수 | 지역 변수 |
| 타입 없음 (동적) | 타입 명시 필수 (정적) |
| 없음 | `var` 키워드로 타입 추론 |

## 핵심 개념

### 1. 메서드 정의

Java의 메서드는 반드시 클래스 안에 존재해야 하고, 반환 타입을 명시해야 한다.

```java
// 반환타입 메서드명(파라미터타입 파라미터명) { 본문 }
public int add(int a, int b) {
    return a + b;
}

// 반환값 없음
public void printGreeting(String name) {
    System.out.println("안녕하세요, " + name);
}
```

### 2. 파라미터 전달 방식

Java는 항상 **값(value)을 복사**해서 전달한다.

#### 기본형 (Primitive Type)

`int`, `long`, `double`, `boolean`, `char` 등은 **값 자체를 복사**한다.
함수 안에서 변경해도 원본이 바뀌지 않는다.

```java
public static void increment(int number) {
    number++;  // 복사본을 증가
}

int x = 10;
increment(x);
System.out.println(x);  // 10 — 원본 불변
```

#### 참조형 (Reference Type)

객체, 배열, String 등은 **주소(참조값)를 복사**한다.
같은 객체를 가리키므로, 객체 내부를 수정하면 원본도 바뀐다.
단, 참조 자체를 다른 객체로 바꾸면 원본에 영향 없다.

```java
public static void addItem(List<String> list) {
    list.add("새 항목");  // 같은 객체 내부 수정 → 원본에 반영됨
}

public static void replaceList(List<String> list) {
    list = new ArrayList<>();  // 참조 교체 → 원본에 영향 없음
}
```

### 3. 변수 스코프

변수가 접근 가능한 범위다.

```
클래스
├── static 변수 (클래스 변수): 클래스 전체에서 접근 가능, 인스턴스 불필요
├── 인스턴스 변수: 인스턴스가 살아 있는 동안 유지
└── 메서드
    └── 지역 변수: 메서드 블록 안에서만 유효, 선언 후 사용 전 반드시 초기화
```

```java
public class ScopeExample {
    static int classVar = 0;   // static 변수 — 모든 인스턴스가 공유
    int instanceVar = 0;       // 인스턴스 변수 — 인스턴스마다 독립

    public void method() {
        int localVar = 10;     // 지역 변수 — 이 메서드 안에서만 유효
    }
}
```

### 4. final 키워드

`final`로 선언된 변수는 한 번 할당 후 **재할당 불가**다.

```java
final int MAX_SIZE = 100;
MAX_SIZE = 200;  // 컴파일 에러

final List<String> list = new ArrayList<>();
list.add("항목");  // 내부 수정은 가능
list = new ArrayList<>();  // 참조 교체는 불가 → 컴파일 에러
```

### 5. var 키워드 (JDK 10+)

지역 변수에 한해 컴파일러가 타입을 추론한다. 우변이 명확할 때만 사용한다.

```java
var name = "홍길동";              // String으로 추론
var list = new ArrayList<Strig>();  // ArrayList<String>으로 추론
var result = add(1, 2);          // int로 추론

// 남용 금지 — 타입이 불명확해지는 경우
var x = getValue();  // getValue()가 무엇을 반환하는지 바로 알 수 없음
```

### 6. 메서드 오버로딩

같은 이름의 메서드를 파라미터 타입 또는 개수를 달리해서 여러 개 정의할 수 있다.
반환 타입만 다른 것은 오버로딩이 아니다.

```java
public int add(int a, int b) { return a + b; }
public double add(double a, double b) { return a + b; }
public int add(int a, int b, int c) { return a + b + c; }
```

## 실습 예제

`CartCalculator.java`에서는 다음을 학습한다:

1. **기본형 전달**: 숫자를 전달했을 때 원본 불변 확인
2. **참조형 전달**: 장바구니 리스트를 받아 아이템 추가 — 원본에 반영됨
3. **final 변수**: 할인율 상수 선언
4. **var 키워드**: 지역 변수 타입 추론 활용

`MethodScopeMain.java`에서는 다음을 학습한다:

1. **스코프 확인**: static / 인스턴스 / 지역 변수의 생명주기
2. **메서드 오버로딩**: 동일 이름, 다른 파라미터로 메서드 정의
3. **실전 예제**: 장바구니 총액 계산, 할인 적용, 세금 계산

## 주의사항

- 지역 변수는 반드시 초기화 후 사용해야 한다 (초기화 없이 사용하면 컴파일 에러)
- `var`는 지역 변수에만 사용 가능하다 (인스턴스 변수, 파라미터에 사용 불가)
- `var`를 사용할 때는 우변만 봐도 타입을 즉시 알 수 있어야 한다
- 참조형 파라미터는 내부 수정이 원본에 반영됨을 항상 인식한다

## 다음 챕터

[Chapter 1-2: 제네릭과 타입 시스템](../chapter_01_2_generic_type/)에서는 Java의 정적 타입 시스템과 제네릭을 배운다.
