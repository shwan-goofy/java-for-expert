# Chapter 0-6: 반복문

## 학습 목표

- `for`, `while`, `do-while` 반복문의 구조와 차이를 이해한다
- 향상된 for문(enhanced for)으로 배열/컬렉션을 순회한다
- `break`와 `continue`로 반복 흐름을 제어한다
- 중첩 반복문을 이해하고 활용한다

## Python과의 비교

| Python | Java |
|---|---|
| `for i in range(5):` | `for (int i = 0; i < 5; i++) { }` |
| `for item in list:` | `for (String item : list) { }` (향상된 for) |
| `while cond:` | `while (cond) { }` |
| `do-while` 문법 없음 | `do { } while (cond);` |
| `break`, `continue` | `break`, `continue` (동일) |

## 핵심 개념

### 1. for문

```java
for (int i = 0; i < 5; i++) {
    System.out.println("i = " + i);
}
// 초기화(int i = 0) -> 조건 검사(i < 5) -> 본문 실행 -> 증감(i++) -> 다시 조건 검사...
```

### 2. while문

조건을 먼저 검사한 후 본문을 실행한다. 조건이 처음부터 거짓이면 **한 번도 실행되지 않을 수 있다**.

```java
int count = 0;
while (count < 3) {
    System.out.println("count = " + count);
    count++;
}
```

### 3. do-while문

본문을 **먼저 한 번 실행한 후** 조건을 검사한다. 최소 1번은 실행이 보장된다.

```java
int num = 10;
do {
    System.out.println("num = " + num);
    num++;
} while (num < 5); // 조건이 거짓이어도 위 코드는 이미 1번 실행됨
```

### 4. 향상된 for문 (enhanced for / for-each)

배열이나 컬렉션의 모든 요소를 순서대로 순회할 때 사용한다. 인덱스가 필요 없을 때 더 간결하다.

```java
int[] numbers = {10, 20, 30};
for (int number : numbers) {
    System.out.println(number);
}
```

### 5. break와 continue

```java
for (int i = 0; i < 10; i++) {
    if (i == 5) {
        break; // 반복문을 즉시 종료
    }
    if (i % 2 == 0) {
        continue; // 이번 반복만 건너뛰고 다음 반복으로
    }
    System.out.println(i); // 1, 3 만 출력됨
}
```

### 6. 중첩 반복문

```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        System.out.print(i + "" + j + " ");
    }
    System.out.println();
}
```

바깥 반복문을 한 번 도는 동안 안쪽 반복문은 전체를 다 돈다. 구구단, 별 찍기 등의 패턴에 자주 사용된다.

## 실습 예제

`LoopsMain.java`에서는 다음을 학습한다:
1. `for`문으로 1부터 5까지 출력
2. `while`, `do-while`의 차이를 조건이 처음부터 거짓인 상황으로 비교
3. 배열을 향상된 `for`문으로 순회
4. `break`, `continue`를 활용한 흐름 제어
5. 중첩 `for`문으로 구구단(2~3단) 출력

## 주의사항

- `for`문 조건을 잘못 설정하면(`i <= n` 대신 `i < n` 실수 등) 무한 반복이나 범위 초과 에러가 발생할 수 있다.
- `while`문에서 반복 조건을 변화시키는 코드(예: `count++`)를 빼먹으면 **무한 루프**에 빠진다.
- 향상된 for문에서는 인덱스에 접근할 수 없다. 인덱스가 필요하면 일반 `for`문을 사용한다.

## 다음 챕터

[Chapter 0-7: 스코프, 형변환](../chapter_00_7_scope_casting/)에서 변수의 유효 범위와 타입 변환을 배운다.
