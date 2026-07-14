# Chapter 0-9: 배열

## 학습 목표

- 배열을 선언, 생성, 초기화하는 여러 방법을 익힌다
- 인덱스를 이용해 배열 요소에 접근하고 순회한다
- 2차원 배열의 구조를 이해한다
- `java.util.Arrays`의 기본 유틸리티 메서드를 사용한다

## Python과의 비교

| Python | Java |
|---|---|
| `list = [1, 2, 3]` (크기 가변, 타입 자유) | `int[] arr = {1, 2, 3};` (크기 고정, 타입 고정) |
| 리스트는 언제든 요소 추가/삭제 가능 | 배열은 **크기가 고정**되어 있어 추가/삭제 불가 |
| `list[0]`, 음수 인덱스 지원 | `arr[0]`, 음수 인덱스 없음 |
| `len(list)` | `arr.length` (메서드 아님, 필드) |
| `list2d = [[1,2],[3,4]]` | `int[][] arr2d = {{1,2},{3,4}};` |

## 핵심 개념

### 1. 배열 선언과 생성

```java
int[] numbers;              // 선언
numbers = new int[5];       // 크기 5인 배열 생성, 기본값(0)으로 채워짐

int[] scores = new int[]{90, 85, 100}; // 생성과 동시에 초기화
int[] ages = {20, 25, 30};             // 축약형 (선언과 동시에만 사용 가능)
```

배열의 길이는 한 번 정하면 **변경할 수 없다**. 크기를 바꾸려면 새 배열을 만들어야 한다.

### 2. 배열 요소 접근과 순회

인덱스는 `0`부터 시작하며, `배열.length - 1`이 마지막 인덱스다.

```java
int[] scores = {90, 85, 100};
System.out.println(scores[0]); // 90
System.out.println(scores.length); // 3 (메서드가 아니라 필드!)

for (int i = 0; i < scores.length; i++) {
    System.out.println(scores[i]);
}

for (int score : scores) { // 향상된 for문
    System.out.println(score);
}
```

범위를 벗어난 인덱스에 접근하면 `ArrayIndexOutOfBoundsException`이 발생한다.

```java
int[] arr = {1, 2, 3};
System.out.println(arr[3]); // 런타임 에러! (인덱스는 0,1,2까지만 유효)
```

### 3. 2차원 배열

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};

System.out.println(matrix[0][1]); // 1행 2열 -> 2
System.out.println(matrix.length);    // 행의 개수 -> 2
System.out.println(matrix[0].length); // 첫 번째 행의 열 개수 -> 3

for (int row = 0; row < matrix.length; row++) {
    for (int col = 0; col < matrix[row].length; col++) {
        System.out.print(matrix[row][col] + " ");
    }
    System.out.println();
}
```

### 4. Arrays 유틸리티

```java
import java.util.Arrays;

int[] numbers = {5, 3, 1, 4, 2};

Arrays.sort(numbers);                 // 오름차순 정렬 (원본 배열 변경)
System.out.println(Arrays.toString(numbers)); // [1, 2, 3, 4, 5] — 배열을 보기 좋게 출력

int[] copy = Arrays.copyOf(numbers, numbers.length); // 배열 복사
```

`System.out.println(numbers)`처럼 배열을 그냥 출력하면 `[I@1b6d3586` 같은 값이 나온다.
배열 내용을 확인하려면 반드시 `Arrays.toString()`을 사용한다.

## 실습 예제

`ArraysMain.java`에서는 다음을 학습한다:
1. 배열 선언, 생성, 초기화 방법 비교
2. 일반 `for`문과 향상된 `for`문으로 배열 순회
3. `ArrayIndexOutOfBoundsException`이 발생하는 상황
4. 2차원 배열 선언과 중첩 반복문 순회
5. `Arrays.sort`, `Arrays.toString`을 활용한 정렬과 출력

## 주의사항

- 배열의 크기는 생성 이후 변경할 수 없다. 가변 크기가 필요하면 이후 배우게 될 `ArrayList` 같은 컬렉션을 사용한다.
- `arr.length`는 메서드가 아니라 필드이므로 괄호 없이 사용한다 (`arr.length()`는 컴파일 에러).
- 배열을 `System.out.println()`으로 직접 출력하면 원하는 값이 나오지 않는다. `Arrays.toString()`을 사용한다.

## 다음 챕터

[Chapter 0-10: 메서드](../chapter_00_10_methods/)에서 반복되는 코드를 재사용 가능한 단위로 묶는 방법을 배운다.
