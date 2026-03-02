# Chapter 1-2: 제네릭과 타입 시스템

## 학습 목표

- Java 정적 타입 시스템과 Python 동적 타입의 차이를 이해한다
- 제네릭 클래스와 제네릭 메서드를 작성한다
- Invariance, Covariance, Contravariance(변성)의 개념과 PECS 원칙을 이해한다
- Type Erasure와 JDK 하위 호환성 구조를 이해한다

## Python과의 비교

| Python | Java |
|--------|------|
| `typing.List[int]` | `List<Integer>` |
| `typing.Optional[str]` | `String` (nullable) 또는 `Optional<String>` |
| `typing.TypeVar('T')` | `<T>` |
| `typing.Union[int, str]` | 제네릭 바운드 또는 인터페이스 |
| 런타임 타입 검사 | 컴파일 타임 타입 검사 |

## 핵심 개념

### 1. 제네릭 클래스

타입을 파라미터로 받는 클래스다. 타입 안전성을 컴파일 타임에 보장한다.

```java
public class Box<T> {
    private T value;

    public void set(T value) { this.value = value; }
    public T get() { return value; }
}

Box<String> stringBox = new Box<>();  // 다이아몬드 연산자 <>
stringBox.set("Hello");
String value = stringBox.get();  // 캐스팅 불필요

Box<Integer> intBox = new Box<>();
intBox.set(42);
```

### 2. 바운디드 타입 파라미터

`extends`로 타입 파라미터의 상한을 제한할 수 있다.

```java
// T는 Comparable을 구현한 타입만 허용
public <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) >= 0 ? a : b;
}

max(10, 20);     // Integer — Comparable 구현
max("ab", "cd"); // String — Comparable 구현
```

### 3. Variance (변성)

제네릭 타입의 상하위 관계를 어떻게 허용하는지에 대한 규칙이다.

#### Invariance (불변성) — Java 기본

`List<Dog>`는 `List<Animal>`의 서브타입이 **아니다**.
두 타입은 완전히 별개의 타입으로 취급된다.

```java
List<Dog> dogs = new ArrayList<>();
// List<Animal> animals = dogs;  // 컴파일 에러!
// 만약 허용된다면? animals.add(new Cat())으로 타입 안전성 파괴
```

#### Covariance (공변성) — `<? extends T>`

`List<? extends Animal>`은 `List<Dog>`, `List<Cat>` 모두 받을 수 있다.
**읽기(Producer)만 가능**, 쓰기 불가 (null 제외).

```java
public static double sumArea(List<? extends Shape> shapes) {
    double total = 0;
    for (Shape shape : shapes) {  // Shape으로 읽기 가능
        total += shape.area();
    }
    return total;
    // shapes.add(new Circle());  // 컴파일 에러 — 어떤 Shape 서브타입인지 알 수 없음
}

List<Circle> circles = List.of(new Circle(5), new Circle(3));
sumArea(circles);  // 가능
```

#### Contravariance (반공변성) — `<? super T>`

`List<? super Dog>`은 `List<Dog>`, `List<Animal>`, `List<Object>` 모두 받을 수 있다.
**쓰기(Consumer)만 권장**, 읽기 시 Object로만 취급.

```java
public static void addDogs(List<? super Dog> list) {
    list.add(new Dog("멍멍이"));  // Dog 추가 가능
    list.add(new Dog("바둑이"));
    // Object obj = list.get(0);  // 읽으면 Object 타입으로만 처리
}

List<Animal> animals = new ArrayList<>();
addDogs(animals);  // 가능
```

#### PECS 원칙 (Producer Extends, Consumer Super)

- 데이터를 **꺼내 쓰는(Producer)** 경우 → `<? extends T>`
- 데이터를 **집어넣는(Consumer)** 경우 → `<? super T>`

```java
// src는 데이터를 제공(Producer) → extends
// dst는 데이터를 받음(Consumer) → super
public static <T> void copy(List<? extends T> src, List<? super T> dst) {
    for (T item : src) {
        dst.add(item);
    }
}
```

### 4. Type Erasure (타입 소거)와 JDK 하위 호환성

#### 배경: JDK 5 이전의 Raw Type

JDK 5 이전에는 제네릭이 없었다. `List`에 모든 타입을 Object로 넣고 꺼낼 때 직접 캐스팅했다.

```java
// JDK 5 이전 방식 (Raw Type)
List list = new ArrayList();
list.add("문자열");
list.add(42);  // 타입 혼재 — 런타임에 ClassCastException 위험
String s = (String) list.get(0);  // 수동 캐스팅
```

#### Type Erasure 원리

JDK 5에서 제네릭 도입 시 하위 호환성 유지를 위해 **컴파일 후 제네릭 타입 정보를 제거**한다.

```java
// 소스 코드
List<String> list = new ArrayList<String>();
list.add("hello");
String s = list.get(0);

// 컴파일 후 바이트코드 (타입 소거)
List list = new ArrayList();
list.add("hello");
String s = (String) list.get(0);  // 컴파일러가 캐스팅 자동 삽입
```

`<T>`는 컴파일 시 `Object`로 치환된다.
바운드가 있으면 (`<T extends Comparable<T>>`) 상한 타입인 `Comparable`로 치환된다.

#### Reifiable vs Non-reifiable Type

- **Reifiable**: 런타임에 타입 정보가 완전히 유지되는 타입
  - `int`, `String`, `List` (raw), `List<?>`, 배열 `String[]`
- **Non-reifiable**: 런타임에 타입 정보가 소거되는 타입
  - `List<String>`, `Map<String, Integer>` 등 제네릭 타입

```java
// instanceof에 제네릭 타입 사용 불가
List<String> list = new ArrayList<>();
// if (list instanceof List<String>) {}  // 컴파일 에러

if (list instanceof List<?>) {}  // 가능 — wildcard는 reifiable
```

#### 주의: Heap Pollution

Raw Type 혼용 시 런타임에 `ClassCastException` 발생 위험이 있다.

```java
List<String> strings = new ArrayList<>();
List rawList = strings;  // 경고 발생 (unchecked assignment)
rawList.add(42);         // 경고 발생 (unchecked call)
String s = strings.get(0);  // 런타임 ClassCastException!
```

## 실습 예제

`Box.java`에서는 다음을 학습한다:
- 기본 제네릭 클래스와 바운디드 타입 파라미터

`GenericUtils.java`에서는 다음을 학습한다:
- 제네릭 메서드 작성
- PECS 원칙 적용 (`copy`, `max`, `min`)

`WildcardExample.java`에서는 다음을 학습한다:
- Invariance / Covariance / Contravariance 구체 예시
- Type Erasure 시뮬레이션

`GenericTypeMain.java`에서는 다음을 학습한다:
- 모든 개념의 통합 실행 및 결과 확인

## 주의사항

- `new T()`처럼 제네릭 타입으로 인스턴스 생성 불가 (Type Erasure 때문)
- `T[]`처럼 제네릭 배열 생성 불가
- `static` 필드에 타입 파라미터 사용 불가 (인스턴스마다 T가 다르므로)
- Raw Type은 레거시 코드 대응 외에 새 코드에서 사용 금지

## 다음 챕터

[Chapter 1-3: 예외 처리](../chapter_01_3_exception/)에서는 Checked/Unchecked 예외와 커스텀 예외를 배운다.
