# Chapter 2-3: 특수 메서드 (Object 메서드 재정의)

## 학습 목표

- Java 모든 클래스의 최상위 부모 `Object`의 핵심 메서드를 이해한다
- `toString()`, `equals()`, `hashCode()`를 올바르게 재정의한다
- `Comparable<T>`와 `Comparator<T>`의 차이를 이해하고 사용한다
- Lombok `@ToString`, `@EqualsAndHashCode`로 보일러플레이트를 제거한다

## Python과의 비교

| Python | Java |
|--------|------|
| `__str__` | `toString()` |
| `__repr__` | (별도 없음 — `toString()`을 상세하게 작성) |
| `__eq__` | `equals()` |
| `__hash__` | `hashCode()` |
| `__lt__`, `__gt__` 등 | `Comparable<T>.compareTo()` |
| `functools.cmp_to_key` | `Comparator<T>` |

## 핵심 개념

### 1. toString()

객체를 문자열로 표현한다. `System.out.println(객체)`, 문자열 연결(`+`) 시 자동 호출.
재정의하지 않으면 `클래스명@해시코드` 형태로 출력된다.

```java
@Override
public String toString() {
    return "Person{name='" + name + "', age=" + age + "}";
}
```

JDK 15+ Text Block을 활용하면 멀티라인 출력을 깔끔하게 작성할 수 있다.

```java
@Override
public String toString() {
    return """
            Person {
              name: %s
              age:  %d
            }""".formatted(name, age);
}
```

### 2. equals()와 hashCode() 계약

#### equals()

기본 `equals()`는 `==` (참조 동등)이다. **값 동등**이 필요하면 반드시 재정의한다.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return age == person.age && Objects.equals(name, person.name);
}
```

#### hashCode() — 반드시 함께 재정의

`equals()`를 재정의하면 **반드시 `hashCode()`도 재정의**해야 한다.
`HashMap`, `HashSet` 등은 `hashCode()`로 버킷을 찾고 `equals()`로 확인하기 때문이다.

> **계약**: `a.equals(b)`가 true이면 `a.hashCode() == b.hashCode()`여야 한다.

```java
@Override
public int hashCode() {
    return Objects.hash(name, age);  // 여러 필드로 해시 계산
}
```

### 3. Comparable<T> — 자연 순서 정의

클래스 자체에 기본 정렬 순서를 부여한다.

```java
public class Product implements Comparable<Product> {
    private final String name;
    private final int price;

    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.price, other.price);  // 가격 오름차순
    }
}

List<Product> products = ...;
Collections.sort(products);  // compareTo() 사용
```

### 4. Comparator<T> — 외부에서 순서 주입

클래스를 수정하지 않고 다양한 정렬 기준을 제공한다.

```java
// 이름 오름차순
Comparator<Product> byName = Comparator.comparing(Product::getName);
// 가격 내림차순
Comparator<Product> byPriceDesc = Comparator.comparingInt(Product::getPrice).reversed();
// 복합 정렬
Comparator<Product> complex = byPriceDesc.thenComparing(byName);

products.sort(byName);
products.sort(byPriceDesc);
```

### 5. Lombok으로 보일러플레이트 제거

`@ToString`, `@EqualsAndHashCode`를 사용하면 컴파일 시 자동으로 메서드를 생성한다.

```java
import lombok.ToString;
import lombok.EqualsAndHashCode;

@ToString
@EqualsAndHashCode
public class Point {
    private final int x;
    private final int y;
}
// Lombok이 toString(), equals(), hashCode() 자동 생성
```

특정 필드 제외:
```java
@ToString(exclude = "password")
@EqualsAndHashCode(exclude = "createdAt")
public class User { ... }
```

`@EqualsAndHashCode(callSuper = true)`: 부모 클래스 필드도 포함.

## 실습 예제

`Vector2D.java`에서는 다음을 학습한다:
- `toString()`, `equals()`, `hashCode()` 직접 구현
- `Comparable<Vector2D>` 구현 (벡터 크기로 비교)
- Text Block 활용

`Product.java`에서는 다음을 학습한다:
- Lombok `@ToString`, `@EqualsAndHashCode` 사용
- `Comparator`로 다양한 정렬 기준 구현

`SpecialMethodsMain.java`에서는 다음을 학습한다:
- HashMap에서 equals/hashCode 계약 중요성 확인
- Collections.sort(), List.sort()와 Comparable/Comparator 연동

## 주의사항

- `equals()`만 재정의하고 `hashCode()`를 재정의하지 않으면 `HashMap`, `HashSet`에서 동작이 깨진다
- `compareTo()`는 일관성을 위해 `equals()`와 같은 필드로 비교하는 것을 권장한다
- `@EqualsAndHashCode`를 JPA `@Entity`에 사용하면 프록시 객체와의 비교에서 문제가 발생할 수 있다

## 다음 챕터

[Chapter 2-4: Builder 패턴과 DSL](../chapter_02_4_builder_dsl/)에서는 Builder 패턴과 Lombok @Builder를 배운다.
