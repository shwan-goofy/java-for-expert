# Chapter 0-12: Object 클래스

## 학습 목표

- 자바의 모든 클래스가 `Object`를 상속받는다는 것을 이해한다
- `equals()`와 `hashCode()`를 왜, 어떻게 함께 재정의해야 하는지 이해한다
- `toString()`을 재정의해 객체를 사람이 읽기 좋은 형태로 출력한다
- `getClass()`로 런타임 타입 정보를 확인한다

## Python과의 비교

| Python | Java |
|---|---|
| 모든 클래스는 암묵적으로 `object` 상속 | 모든 클래스는 암묵적으로 `Object` 상속 |
| `__eq__`, `__hash__` 재정의 | `equals()`, `hashCode()` 재정의 |
| `__repr__`, `__str__` | `toString()` |
| `type(obj)` | `obj.getClass()` |
| `==`가 기본적으로 객체 동일성(is와 같음) | `==`는 항상 참조 비교, 내용 비교는 `equals()` |

## 핵심 개념

### 1. Object — 모든 클래스의 부모

```java
public class Person {
    // extends Object 를 생략해도 자동으로 Object를 상속받는다
}
```

`Object`는 모든 클래스가 공통으로 갖는 기본 메서드를 제공한다: `equals()`, `hashCode()`, `toString()`,
`getClass()` 등. 이 메서드들은 `Object`의 기본 구현이 있지만, 대부분 클래스 목적에 맞게 재정의해서 사용한다.

### 2. == 와 equals() 의 차이

`==`는 기본형이면 값을, 참조형이면 **주소(참조)**를 비교한다. `Object`의 기본 `equals()`도 내부적으로
`==`와 동일하게 동작한다 (재정의하지 않으면 주소 비교와 같음).

```java
public class Point {
    private final int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }
}

Point p1 = new Point(1, 2);
Point p2 = new Point(1, 2);

System.out.println(p1 == p2);       // false — 서로 다른 객체(주소가 다름)
System.out.println(p1.equals(p2));  // false — equals를 재정의하지 않아 == 와 동일하게 동작
```

값이 같으면 같은 객체로 취급하고 싶다면 `equals()`를 재정의해야 한다.

```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;                    // 1. 자기 자신과 비교
    if (obj == null || getClass() != obj.getClass()) return false; // 2. null, 타입 체크
    Point other = (Point) obj;                        // 3. 캐스팅
    return this.x == other.x && this.y == other.y;    // 4. 필드 값 비교
}
```

### 3. equals()와 hashCode()는 항상 함께 재정의한다

`HashMap`, `HashSet` 같은 해시 기반 컬렉션은 `hashCode()`로 먼저 버킷을 찾고,
그 안에서 `equals()`로 실제 동등성을 확인한다. **equals가 true인 두 객체는 반드시 hashCode도 같아야 한다**는
계약(contract)이 있다. 이 계약을 어기면 `HashSet`에 중복 값이 들어가는 등 예상치 못한 버그가 생긴다.

```java
@Override
public int hashCode() {
    return Objects.hash(x, y); // java.util.Objects의 유틸리티 사용 (직접 계산보다 안전하고 간단)
}
```

### 4. toString() 재정의

기본 `toString()`은 `클래스명@해시코드16진수` 형태(예: `Point@1b6d3586`)로 사람이 읽기 어렵다.

```java
@Override
public String toString() {
    return "Point{x=" + x + ", y=" + y + "}";
}
```

`System.out.println(obj)`나 문자열 연결(`"값: " + obj`)에서 자동으로 `toString()`이 호출된다.

### 5. getClass()

객체의 런타임 타입을 확인할 때 사용한다. `equals()` 구현에서 타입 체크 용도로도 자주 쓰인다.

```java
Point p = new Point(1, 2);
System.out.println(p.getClass().getName());      // org.example.chapter00_12.Point
System.out.println(p.getClass().getSimpleName()); // Point
```

## 실습 예제

`Point.java`에서는 다음을 학습한다:
1. `equals()`, `hashCode()`, `toString()` 재정의
2. `Objects.hash()`를 활용한 안전한 해시코드 생성

`ObjectClassMain.java`에서는 다음을 학습한다:
1. `==`와 `equals()`의 차이 확인
2. `equals`/`hashCode`를 재정의하지 않았을 때와 재정의했을 때 `HashSet` 동작 비교
3. `toString()` 재정의 전후 출력 결과 비교
4. `getClass()`로 런타임 타입 확인

## 주의사항

- `equals()`를 재정의하면서 `hashCode()`를 빠뜨리면, `HashMap`/`HashSet`에서 중복 제거가 제대로 되지 않는 버그가 생긴다.
- `equals()` 파라미터 타입은 반드시 `Object`여야 한다. `equals(Point other)`처럼 타입을 좁히면 오버라이딩이 아니라 **오버로딩**이 되어버려 실제로는 재정의되지 않는다.
- `toString()`을 재정의하지 않으면 로그나 디버깅 출력에서 객체 내용을 알아보기 어렵다.

## 다음 챕터

[Chapter 0-13: 불변 객체](../chapter_00_13_immutable_object/)에서 상태가 변하지 않는 객체를 설계하는 방법을 배운다.
